package eu.iamgio.botmaker.ui.console

import eu.iamgio.botmaker.TelejamBot
import eu.iamgio.botmaker.lib.BotConfiguration
import eu.iamgio.botmaker.loadBotConfiguration
import eu.iamgio.botmaker.newTelejamBot
import eu.iamgio.botmaker.ui.splitcontrols.ConsoleSplitControl
import io.github.ageofwar.telejam.TelegramException
import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.layout.VBox
import kotlin.concurrent.thread

/**
 * @author Giorgio Garofalo
 */
class ConsoleNode(private val consoleControl: ConsoleSplitControl) : VBox() {

    val runningProperty = SimpleBooleanProperty()

    private var telejamBot: TelejamBot? = null
    private var botThread: Thread? = null

    init {
        stylesheets += "/css/console.css"
        styleClass += "console"
    }

    fun run() {
        runningProperty.set(true)
        children.clear()
        botThread = thread(isDaemon = true) {
            try {
                telejamBot = newTelejamBot(loadBotConfiguration(consoleControl.botName), consoleControl.logger)
                telejamBot!!.run()
            } catch (e: TelegramException) {
                stop()
                e.printStackTrace()
            }
        }
    }

    fun stop() {
        botThread?.interrupt()
        botThread?.join()
        telejamBot?.close()
        runningProperty.set(false)
    }
}