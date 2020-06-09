package eu.iamgio.botmaker.ui.botcontrol

import eu.iamgio.botmaker.lib.BotConfiguration
import eu.iamgio.botmaker.root
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

/**
 * Pane which covers the whole second part of the main SplitPane. Contains all the controls needed to make/edit a bot.
 * @author Giorgio Garofalo
 */
class BotControlPane(bot: BotConfiguration) : VBox() {

    init {
        styleClass += "bot-control-pane"

        children += HBox().apply {
            alignment = Pos.CENTER_LEFT
            children += Label(bot.name).apply { styleClass += "title" }
            children += TokenBox(bot)
        }
    }

    fun show() {
        root.rightControl.showBotControl(this)
    }
}