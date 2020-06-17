package eu.iamgio.botmaker.ui.botcontrol.event

import eu.iamgio.botmaker.ui.withClass
import javafx.scene.control.Label
import javafx.scene.layout.FlowPane

/**
 * @author Giorgio Garofalo
 */
class EventLine : FlowPane() {

    val hintLabel = Label().withClass("hint")

    init {
        hgap = 10.0
    }
}