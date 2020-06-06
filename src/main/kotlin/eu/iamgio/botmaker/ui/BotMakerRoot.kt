package eu.iamgio.botmaker.ui

import javafx.scene.control.SplitPane
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.VBox

/**
 * @author Giorgio Garofalo
 */
class BotMakerRoot : AnchorPane() {

    private val leftControl = VBox().apply {
        SplitPane.setResizableWithParent(this, false)
        styleClass += "left-control"
    }

    private val rightControl = VBox().apply {
        SplitPane.setResizableWithParent(this, false)
        styleClass += "right-control"
    }

    init {
        val splitPane = SplitPane(leftControl, rightControl).apply {
            bindSize(this@BotMakerRoot)
            setDividerPositions(0.25)
        }
        children += splitPane
    }
}