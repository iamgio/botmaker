package eu.iamgio.botmaker.ui.botcontrol

import animatefx.animation.FadeInUp
import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.lib.BotConfiguration
import eu.iamgio.botmaker.root
import eu.iamgio.botmaker.save
import eu.iamgio.botmaker.ui.botcontrol.event.EventNode
import eu.iamgio.botmaker.ui.withClass
import io.github.ageofwar.telejam.messages.Message
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

/**
 * Pane which covers the whole second part of the main SplitPane. Contains all the controls needed to make/edit a bot.
 * @author Giorgio Garofalo
 */
class BotControlPane(val name: String, bot: BotConfiguration) : VBox() {

    var botToken = bot.botToken
    private val eventsVBox = VBox()

    init {
        styleClass += "bot-control-pane"
        stylesheets += "/css/botcontrol.css"

        children += HBox().apply {
            alignment = Pos.CENTER_LEFT
            children += Label(name).withClass("title")
            children += TokenBox(this@BotControlPane)
        }

        children += Label("+ ${getString("new.event")}").withClass("new").apply {
            setOnMouseClicked {
                println("New event")
                // TODO new event
            }
        }

        children += ScrollPane(eventsVBox).withClass("edge-to-edge")

        bot.messageEvents.forEach {
            eventsVBox.children += EventNode(it, this)
        }
    }

    fun show() {
        FadeInUp(this).setSpeed(2.0).play()
        root.rightControl.showBotControl(this)
    }

    fun toBotConfiguration(): BotConfiguration {
        return BotConfiguration(
                botToken,
                eventsVBox.children
                        .filterIsInstance<EventNode<Message>>()
                        .map { it.toEvent() }
        )
    }

    fun save() = toBotConfiguration().save(name)

    fun autosave() {
        if(true /* TODO get autosave boolean from settings */) save()
    }
}