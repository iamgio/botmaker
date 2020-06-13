package eu.iamgio.botmaker.ui.botcontrol.event

import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.lib.Event
import eu.iamgio.botmaker.lib.EventComponent
import eu.iamgio.botmaker.ui.withClass
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.FlowPane
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

        addAction(event.filter)
        addAction(event.action)
    }

    private fun addAction(eventComponent: EventComponent<*>) {
        val graphics = eventComponent.toUI()
        val flowPane = FlowPane().apply { hgap = 10.0 }

        graphics.forEach {
            flowPane.children += when(it) {
                is EventComponent.EventComponentText -> Label(getString(it.textKey)).withClass("event-action")
                is EventComponent.EventComponentField -> TextField()
                else -> null
            }
        }

        actionsVBox.children.add(actionsVBox.children.size - 1, flowPane)
    }
}