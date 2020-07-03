package eu.iamgio.botmaker.ui.console

import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.lib.ConsoleLogger
import eu.iamgio.botmaker.lib.telejam.description
import eu.iamgio.botmaker.ui.withClass
import io.github.ageofwar.telejam.messages.Message
import javafx.application.Platform
import javafx.scene.control.Label

/**
 * @author Giorgio Garofalo
 */
class UIConsoleLogger(private val console: ConsoleNode) : ConsoleLogger {

    override fun log(text: String) {
        Platform.runLater { console.children += Label(text).withClass("log") }
    }

    override fun logMessage(message: Message) {
        Platform.runLater { console.children += Label(getString("event.MessageEventNode.log.${message::class.simpleName}", message.chat.title, message.description ?: "empty caption")).withClass("log") }
    }

    override fun logError(text: String) {
        Platform.runLater { console.children += Label(text).withClass("log").withClass("log-error") }
    }
}