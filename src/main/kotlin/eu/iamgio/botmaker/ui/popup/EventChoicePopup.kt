package eu.iamgio.botmaker.ui.popup

import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.root
import eu.iamgio.botmaker.ui.Actionable
import eu.iamgio.botmaker.ui.BrowsableVBox
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.layout.HBox

/**
 * @author Giorgio Garofalo
 */
class EventChoicePopup(type: ChoiceType, items: List<*>) : ScenePopup(getString("popup.event-choice.title", type.name.toLowerCase())) {

    enum class ChoiceType {
        EVENT, FILTER, ACTION
    }

    init {
        children += HBox().apply {
            styleClass += "event-choice"
            children += EventChoiceBrowsableList(items).also { nodeToFocus = it }
        }

        addConfirmButton("popup.event-choice.confirm")

        root.rightControl.disableProperty().bind(shownProperty)
    }

    override fun onConfirm() {
        println("abc")
        hide()
    }
}

class EventChoiceBrowsableList(items: List<*>) : BrowsableVBox(true) {

    init {
        styleClass += "event-choice-list"
        children.addAll(items.map { EventChoiceLabel(it.toString()) })
    }

    class EventChoiceLabel(text: String) : Label(text), Actionable {

        override fun onAction(keyCode: KeyCode) {
            if(keyCode == KeyCode.ENTER) {
                println("Confirmed $text")
                // TODO add to bot control
            }
        }
    }
}