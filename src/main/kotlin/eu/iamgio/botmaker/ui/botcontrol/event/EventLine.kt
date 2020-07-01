package eu.iamgio.botmaker.ui.botcontrol.event

import eu.iamgio.botmaker.ui.SVG_CLOSE
import eu.iamgio.botmaker.ui.createSvg
import eu.iamgio.botmaker.ui.withClass
import eu.iamgio.botmaker.ui.wrap
import javafx.application.Platform
import javafx.scene.control.Label
import javafx.scene.layout.FlowPane

/**
 * @author Giorgio Garofalo
 */
class EventLine : FlowPane() {

    val hintLabel = Label().withClass("hint")

    private var onRemove: () -> Unit = {}

    init {
        hgap = 10.0
        styleClass += "event-line"

        children += createSvg(SVG_CLOSE).wrap().apply {
            Platform.runLater {
                setOnMouseClicked {
                    onRemove()
                }
            }
        }.withClass("remove-line-button")
    }

    fun setOnRemove(onRemove: () -> Unit) {
        this.onRemove = onRemove
    }
}