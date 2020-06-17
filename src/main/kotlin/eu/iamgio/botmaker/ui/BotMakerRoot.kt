package eu.iamgio.botmaker.ui

import eu.iamgio.botmaker.Settings
import eu.iamgio.botmaker.lib.BotConfiguration
import eu.iamgio.botmaker.ui.splitcontrols.LeftSplitControl
import eu.iamgio.botmaker.ui.splitcontrols.RightSplitControl
import javafx.scene.control.SplitPane
import javafx.scene.layout.AnchorPane

/**
 * @author Giorgio Garofalo
 */
class BotMakerRoot(bots: MutableMap<String, BotConfiguration>, settings: Settings) : AnchorPane() {

    private val splitPane = SplitPane().apply {
        bindSize(this@BotMakerRoot)
        setDividerPositions(0.25)
    }

    val leftControl = LeftSplitControl(bots, settings)
    val rightControl = RightSplitControl()

    init {
        splitPane.items.addAll(leftControl, rightControl)
        children += splitPane
    }
}