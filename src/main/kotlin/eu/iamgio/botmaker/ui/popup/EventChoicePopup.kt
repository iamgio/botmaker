package eu.iamgio.botmaker.ui.popup

import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.root
import eu.iamgio.botmaker.ui.Actionable
import eu.iamgio.botmaker.ui.BrowsableVBox
import eu.iamgio.botmaker.ui.withClass
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.input.KeyCode
import javafx.scene.layout.HBox

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
            children += ScrollPane().withClass("edge-to-edge").apply {
                content = EventChoiceBrowsableList(items, this).also {
                    it.applyCss()
                    it.layout()
                    nodeToFocus = it
                    maxHeight = 400.0
                }
            }
        }

        addConfirmButton("popup.event-choice.confirm")

        root.rightControl.disableProperty().bind(shownProperty)
    }

    override fun onConfirm() {
        println("abc")
        hide()
    }
}

class EventChoiceBrowsableList<T>(items: List<T>, scrollPane: ScrollPane) : BrowsableVBox(true, scrollPane) {

    init {
        styleClass += "event-choice-list"
        children.addAll(items.map { EventChoiceLabel(it) })
    }

    inner class EventChoiceLabel(item: T) : Label(item.toString()), Actionable {

        init {
            prefHeight = 50.0
        }

        override fun onAction(keyCode: KeyCode) {
            if(keyCode == KeyCode.ENTER) {
                println("Confirmed $text")
                // TODO add to bot control
            }
        }
    }
}