package eu.iamgio.botmaker.ui.botcontrol

import animatefx.animation.FadeInUp
import eu.iamgio.botmaker.Settings
import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.lib.BotConfiguration
import eu.iamgio.botmaker.lib.Event
import eu.iamgio.botmaker.root
import eu.iamgio.botmaker.save
import eu.iamgio.botmaker.ui.SVG_CONSOLE
import eu.iamgio.botmaker.ui.botcontrol.event.MessageEventNode
import eu.iamgio.botmaker.ui.createSvg
import eu.iamgio.botmaker.ui.splitcontrols.ConsoleSplitControl
import eu.iamgio.botmaker.ui.withClass
import eu.iamgio.botmaker.ui.wrap
import io.github.ageofwar.telejam.messages.Message
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox

/**
 * Pane which covers the whole second part of the main SplitPane. Contains all the controls needed to make/edit a bot.
 * @author Giorgio Garofalo
 */
class BotControlPane(
        val name: String,
        val bot: BotConfiguration,
        private val settings: Settings) : VBox() {

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
                val newEvent = Event<Message>() //TODO choice
                bot.messageEvents += newEvent
                eventsVBox.children += MessageEventNode(newEvent, this@BotControlPane)
            }
        }

        children += Pane(createSvg(SVG_CONSOLE).wrap().withClass("console-svg").apply {
            var consoleControl = ConsoleSplitControl()
            setOnMouseClicked {
                if(root.consoleControl == null) {
                    consoleControl = root.addConsole(consoleControl)
                } else {
                    root.removeConsole()
                }
            }
        })

        children += ScrollPane(eventsVBox).withClass("edge-to-edge")

        bot.messageEvents.forEach {
            eventsVBox.children += MessageEventNode(it, this)
        }
    }

    fun show() {
        FadeInUp(this).setSpeed(2.0).play()
        root.rightControl.showBotControl(this)
    }

    fun save() = bot.save(name)

    fun autosave() {
        if(settings.autoSave) save()
    }

    fun openConsole() {
        autosave()
        root.addConsole()
    }
}