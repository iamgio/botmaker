package eu.iamgio.botmaker.ui

import eu.iamgio.botmaker.Settings
import eu.iamgio.botmaker.lib.BotConfiguration
import eu.iamgio.botmaker.ui.splitcontrols.ConsoleSplitControl
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

    val consoleControl: ConsoleSplitControl?
        get() = if(splitPane.items.size == 3) splitPane.items[2] as ConsoleSplitControl else null

    init {
        splitPane.items.addAll(leftControl, rightControl)
        children += splitPane
    }

    fun addConsole(botName: String, consoleSplitControl: ConsoleSplitControl = ConsoleSplitControl(botName)): ConsoleSplitControl {
        return with(splitPane.items) {
            if(size == 2) {
                add(consoleSplitControl)
            } else {
                consoleControl?.stopBot()
                set(2, consoleSplitControl)
            }
            splitPane.setDividerPosition(1, .7)
            consoleSplitControl
        }
    }

    fun removeConsole() {
        if(splitPane.items.size == 3) {
            consoleControl?.stopBot()
            splitPane.items.removeAt(2)
        }
    }
}