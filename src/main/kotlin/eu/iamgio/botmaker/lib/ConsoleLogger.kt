package eu.iamgio.botmaker.lib

import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.lib.telejam.description
import io.github.ageofwar.telejam.messages.Message

/**
 * @author Giorgio Garofalo
 */
interface ConsoleLogger {
    fun log(text: String)
    fun logMessage(message: Message)
    fun logError(text: String)
}

class CLIConsoleLogger : ConsoleLogger {
    override fun log(text: String) {
        println(text)
    }

    override fun logMessage(message: Message) {
        println(getString("event.MessageEventNode.log.${message::class.simpleName}", message.chat.title, message.description ?: "empty caption"))
    }

    override fun logError(text: String) {
        System.err.println(text)
    }
}