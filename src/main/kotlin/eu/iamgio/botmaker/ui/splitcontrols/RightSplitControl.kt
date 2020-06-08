package eu.iamgio.botmaker.ui.splitcontrols

import eu.iamgio.botmaker.ui.botcontrol.BotControlPane

/**
 * @author Giorgio Garofalo
 */
class RightSplitControl : SplitControl() {

    init {
        styleClass += "right-control"
    }

    fun showBotControl(botControlPane: BotControlPane) {
        if(children.size == 1) {
            children += botControlPane
        } else {
            children[1] = botControlPane
        }
    }
}