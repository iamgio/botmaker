package eu.iamgio.botmaker.ui.splitcontrols

import eu.iamgio.botmaker.ui.botcontrol.BotControlPane
import eu.iamgio.botmaker.ui.menubar.createMenuBar
import javafx.application.Platform

/**
 * @author Giorgio Garofalo
 */
class RightSplitControl : SplitControl() {

    init {
        styleClass += "right-control"

        Platform.runLater {
            val menuBar = createMenuBar()
            navBar.children += menuBar
        }
    }

    fun showBotControl(botControlPane: BotControlPane) {
        if(children.size == 1) {
            children += botControlPane
        } else {
            children[1] = botControlPane
        }
    }
}