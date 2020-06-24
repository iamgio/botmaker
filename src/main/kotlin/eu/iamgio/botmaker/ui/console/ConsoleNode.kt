package eu.iamgio.botmaker.ui.console

import eu.iamgio.botmaker.TelejamBot
import eu.iamgio.botmaker.lib.BotConfiguration
import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.layout.VBox
import kotlin.concurrent.thread

/**
 * @author Giorgio Garofalo
 */
class ConsoleNode : VBox() {

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
        thread {
            telejamBot = TelejamBot(bot).also { it.run() }
        }
    }

    fun stop() {
        runningProperty.set(false)
        telejamBot?.close()
    }
}