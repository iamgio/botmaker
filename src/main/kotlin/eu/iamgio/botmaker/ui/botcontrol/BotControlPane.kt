package eu.iamgio.botmaker.ui.botcontrol

import animatefx.animation.FadeInUp
import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.lib.BotConfiguration
import eu.iamgio.botmaker.lib.IfMessageStartsWith
import eu.iamgio.botmaker.lib.MessageEvent
import eu.iamgio.botmaker.lib.Reply
import eu.iamgio.botmaker.root
import eu.iamgio.botmaker.ui.botcontrol.event.EventNode
import eu.iamgio.botmaker.ui.withClass
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

/**
 * Pane which covers the whole second part of the main SplitPane. Contains all the controls needed to make/edit a bot.
 * @author Giorgio Garofalo
 */
class BotControlPane(val bot: BotConfiguration) : VBox() {

    private val eventsVBox = VBox()

    init {
        styleClass += "bot-control-pane"
        stylesheets += "/css/botcontrol.css"

        children += HBox().apply {
            alignment = Pos.CENTER_LEFT
            children += Label(bot.name).withClass("title")
            children += TokenBox(bot, this@BotControlPane)
        }

        children += Label("+ ${getString("new.event")}").withClass("new").apply {
            setOnMouseClicked {
                println("New event")
                // TODO new event
            }
        }

        children += ScrollPane(eventsVBox).withClass("edge-to-edge")
        val event = MessageEvent(IfMessageStartsWith("test"), Reply("abc")) // Test
        eventsVBox.children += EventNode(event)
    }

    fun show() {
        FadeInUp(this).setSpeed(2.0).play()
        root.rightControl.showBotControl(this)
    }

    fun save() = bot.save()

    fun autosave() {
        if(true /* TODO get autosave boolean from settings */) save()
    }
}