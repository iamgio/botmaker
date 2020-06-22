package eu.iamgio.botmaker.ui.popup

import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.lib.Action
import eu.iamgio.botmaker.lib.Filter
import eu.iamgio.botmaker.root
import eu.iamgio.botmaker.ui.Actionable
import eu.iamgio.botmaker.ui.BrowsableVBox
import eu.iamgio.botmaker.ui.withClass
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.input.KeyCode
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

/**
 * @author Giorgio Garofalo
 */
class EventChoicePopup<T>(type: ChoiceType, items: List<T>) : ScenePopup(getString("popup.event-choice.title", type.name.toLowerCase())) {

    enum class ChoiceType {
        EVENT, FILTER, ACTION
    }

    init {
        children += HBox().apply {
            styleClass += "event-choice"
            val descriptionBox = EventChoiceDescriptionBox()
            children += ScrollPane().withClass("edge-to-edge").apply {
                content = EventChoiceBrowsableList(items, this, descriptionBox).also {
                    it.applyCss()
                    it.layout()
                    nodeToFocus = it
                    maxHeight = 400.0
                }
            }
            children += descriptionBox
        }

        addConfirmButton("popup.event-choice.confirm")

        root.rightControl.disableProperty().bind(shownProperty)
    }

    override fun onConfirm() {
        println("abc")
        hide()
    }
}

class EventChoiceBrowsableList<T>(items: List<T>, scrollPane: ScrollPane, descriptionBox: EventChoiceDescriptionBox) : BrowsableVBox(true, scrollPane) {

    init {
        styleClass += "event-choice-list"
        children.addAll(items.map { EventChoiceLabel(it) })

        indexProperty.addListener { _, _, index ->
            if(index is Int && index >= 0) {
                when(val item = (children[index] as EventChoiceBrowsableList<*>.EventChoiceLabel).item) {
                    is Filter<*> -> descriptionBox.update(item)
                    is Action<*> -> descriptionBox.update(item)
                }
            } else {
                descriptionBox.updateNoContent()
            }
        }
    }

    inner class EventChoiceLabel(val item: T) : Label(), Actionable {

        init {
            prefHeight = 50.0
            text = getString(when(item) {
                is Filter<*> -> "event.filter.${item.javaClass.simpleName}.text"
                is Action<*> -> "event.action.${item.javaClass.simpleName}.text"
                else -> item.toString()
            }) + "..."
        }

        override fun onAction(keyCode: KeyCode) {
            if(keyCode == KeyCode.ENTER) {
                println("Confirmed $text")
                // TODO add to bot control
            }
        }
    }
}

class EventChoiceDescriptionBox : VBox() {

    private val titleLabel = Label().withClass("description-title")
    private val descriptionLabel = Label().withClass("description-text").apply { isWrapText = true }

    init {
        styleClass += "description-box"
        prefWidthProperty().bind(root.prefWidthProperty().divide(4))

        updateNoContent()
        children.addAll(titleLabel, descriptionLabel)
    }

    private fun update(key: String) {
        titleLabel.text = getString("$key.text")
        descriptionLabel.text = getString("$key.description")
    }

    fun update(filter: Filter<*>) = update("event.filter.${filter.javaClass.simpleName}")
    fun update(action: Action<*>) = update("event.action.${action.javaClass.simpleName}")

    fun updateNoContent() = update("popup.event-choice.no-content")
}