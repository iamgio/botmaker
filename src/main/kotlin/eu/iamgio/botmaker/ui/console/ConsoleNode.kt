package eu.iamgio.botmaker.ui.console

import eu.iamgio.botmaker.TelejamBot
import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.lib.BotConfiguration
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

    lateinit var bot: BotConfiguration

    private var telejamBot: TelejamBot? = null

    init {
        stylesheets += "/css/console.css"
        prefHeight = 20.0
        styleClass += "console"
    }

    fun run() {
        runningProperty.set(true)
        children.clear()
        thread {
            try {
                consoleControl.log(getString("console.log.start"))
                telejamBot = TelejamBot(bot).also { it.run() }
            } catch(e: TelegramException) {
                consoleControl.logError(getString("console.log.error", e.message ?: ""))
                if(e.errorCode == 401) consoleControl.logError(getString("console.log.unauthorized"))
                e.printStackTrace()
            }
        }
    }

    fun stop() {
        runningProperty.set(false)
        telejamBot?.close()
    }
}