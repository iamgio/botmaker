package eu.iamgio.botmaker.ui.popup

import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.lib.Action
import eu.iamgio.botmaker.lib.Filter
import eu.iamgio.botmaker.root
import eu.iamgio.botmaker.ui.Actionable
import eu.iamgio.botmaker.ui.BrowsableVBox
import eu.iamgio.botmaker.ui.botcontrol.event.EventNode
import eu.iamgio.botmaker.ui.withClass
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.input.KeyCode
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

/**
 * @author Giorgio Garofalo
 */
class EventChoicePopup<T, R>(type: ChoiceType, items: List<T>, val eventNode: EventNode<R>) : ScenePopup(getString("popup.event-choice.title", type.name.toLowerCase())) {

    enum class ChoiceType {
        EVENT, FILTER, ACTION
    }

    private val browsableList: EventChoiceBrowsableList<T, R>

    init {
        children += HBox().apply {
            styleClass += "event-choice"
            val descriptionBox = EventChoiceDescriptionBox()
            children += ScrollPane().withClass("edge-to-edge").apply {
                browsableList = EventChoiceBrowsableList(items, this, this@EventChoicePopup, descriptionBox).also {
                    it.applyCss()
                    it.layout()
                    nodeToFocus = it
                    maxHeight = 400.0
                }
                content = browsableList
            }
            children += descriptionBox
        }

        addConfirmButton("popup.event-choice.confirm", hasPriority = true)

        root.rightControl.disableProperty().bind(shownProperty)
    }

    override fun onConfirm() {
        if(browsableList.indexProperty.value >= 0) {
            val selected = browsableList.children[browsableList.indexProperty.value]
            (selected as? EventChoiceBrowsableList.EventChoiceLabel<T, R>)?.onAction(KeyCode.ENTER)
        }
    }
}

class EventChoiceBrowsableList<T, R>(items: List<T>, scrollPane: ScrollPane, popup: EventChoicePopup<T, R>, descriptionBox: EventChoiceDescriptionBox) : BrowsableVBox(true, scrollPane) {

    init {
        styleClass += "event-choice-list"
        children.addAll(items.map { EventChoiceLabel(it, popup) })

        indexProperty.addListener { _, _, index ->
            if(index is Int && index >= 0) {
                when(val item = (children[index] as EventChoiceLabel<*, *>).item) {
                    is Filter<*> -> descriptionBox.update(item)
                    is Action<*> -> descriptionBox.update(item)
                }
            } else {
                descriptionBox.updateNoContent()
            }
        }
    }

    class EventChoiceLabel<T, R>(val item: T, private val popup: EventChoicePopup<T, R>) : Label(), Actionable {

        init {
            prefHeight = 50.0
            text = getString(when(item) {
                is Filter<*> -> "event.filter.${item.javaClass.simpleName}.text"
                is Action<*> -> "event.action.${item.javaClass.simpleName}.text"
                else -> item.toString()
            }) + "..."

            setOnMouseClicked {
                if(it.clickCount == 2) onAction(KeyCode.ENTER)
            }
        }

        @Suppress("UNCHECKED_CAST")
        override fun onAction(keyCode: KeyCode) {
            if(keyCode == KeyCode.ENTER) {
                popup.hide()
                println("Confirmed $text")
                with(popup.eventNode) {
                    when(item) {
                        is Filter<*> -> (item as Filter<R>).let {
                            event.filters.filters += it
                            addFilter(it)
                        }
                        is Action<*> -> (item as Action<R>).let {
                            event.actions.actions += it
                            addAction(it)
                        }
                    }
                    popup.eventNode.botControlPane.autosave()
                }
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