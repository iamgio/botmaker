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

    init {
        styleClass += "event"

        children += Label(getString("event.name." + event.javaClass.simpleName)).withClass("event-title")
    }
}