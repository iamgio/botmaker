package eu.iamgio.botmaker.ui.botcontrol.event

import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.lib.Event
import eu.iamgio.botmaker.ui.withClass
import javafx.scene.control.Label
import javafx.scene.layout.VBox

/**
 * @author Giorgio Garofalo
 */
class EventNode(event: Event<*>) : VBox() {

    private val actionsVBox = VBox().withClass("actions")

    init {
        styleClass += "event"

        children += Label(getString("event.name." + event.javaClass.simpleName) + ":").withClass("event-title")

        children += actionsVBox

        actionsVBox.children += Label("+ ${getString("new.action")}").withClass("new").apply {
            setOnMouseClicked {
                println("New action")
                // TODO new action
            }
        }

        addAction("Test bla bla bla")
    }

    fun addAction(text: String) {
        actionsVBox.children.add(actionsVBox.children.size - 1, Label(text).withClass("event-action"))
    }
}