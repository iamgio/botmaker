package eu.iamgio.botmaker.ui

import eu.iamgio.botmaker.ui.splitcontrols.LeftSplitControl
import eu.iamgio.botmaker.ui.splitcontrols.RightSplitControl
import javafx.scene.control.SplitPane
import javafx.scene.layout.AnchorPane

/**
 * @author Giorgio Garofalo
 */
class BotMakerRoot : AnchorPane() {

    private val splitPane = SplitPane().apply {
        bindSize(this@BotMakerRoot)
        setDividerPositions(0.25)
    }

    private val leftControl = LeftSplitControl()
    private val rightControl = RightSplitControl()

    init {
        splitPane.items.addAll(leftControl, rightControl)
        children += splitPane
    }
}