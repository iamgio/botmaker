package eu.iamgio.botmaker.lib

import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.lib.telejam.description
import eu.iamgio.botmaker.toErrorKey
import io.github.ageofwar.telejam.Bot
import io.github.ageofwar.telejam.TelegramException
import io.github.ageofwar.telejam.messages.Message
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * @author Giorgio Garofalo
 */
interface ConsoleLogger {
    fun log(text: String)
    fun logStart(bot: Bot) {
        log(getString("console.log.start", bot.username))
    }
    fun logStop(bot: Bot) {
        log(getString("console.log.stop", bot.username))
    }

    fun logMessage(message: Message)
    fun logMessageSent(message: Message) = logMessage(message)

    fun logError(text: String)
    fun logError(throwable: Throwable) {
        if (throwable is TelegramException) {
            logError(getString(toErrorKey(throwable.errorCode), throwable.message ?: "Unknown error", throwable.errorCode.toString()))
        } else {
            logError(throwable.message ?: "Unknown error (${throwable::class.qualifiedName})")
        }
    }
}

class CLIConsoleLogger(private val dateTimeFormatter: DateTimeFormatter) : ConsoleLogger {
    override fun log(text: String) {
        println(format(text))
    }

    override fun logMessage(message: Message) {
        println(format(getString("event.MessageEventNode.log.${message::class.simpleName}", message.chat.title, message.description ?: "empty caption")))
    }

    override fun logError(text: String) {
        System.err.println(format(text))
    }

    private fun format(text: String, date: LocalDateTime = LocalDateTime.now()) = getString("console.log.format", date.format(dateTimeFormatter), text)
}