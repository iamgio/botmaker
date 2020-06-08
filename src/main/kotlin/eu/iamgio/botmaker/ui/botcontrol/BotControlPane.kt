package eu.iamgio.botmaker.ui.botcontrol

import eu.iamgio.botmaker.root
import javafx.scene.control.Label
import javafx.scene.layout.VBox

/**
 * Pane which covers the whole second part of the main SplitPane. Contains all the controls needed to make/edit a bot.
 * @author Giorgio Garofalo
 */
class BotControlPane(name: String /* TODO bot */) : VBox() {

    init {
        styleClass += "bot-control-pane"

        children += Label(name).apply { styleClass += "title" }
    }

    fun show() {
        root.rightControl.showBotControl(this)
    }
}