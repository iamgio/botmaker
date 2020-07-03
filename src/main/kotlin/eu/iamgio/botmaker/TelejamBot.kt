package eu.iamgio.botmaker

import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.lib.BotConfiguration
import eu.iamgio.botmaker.lib.ConsoleLogger
import eu.iamgio.botmaker.lib.Event
import io.github.ageofwar.telejam.Bot
import io.github.ageofwar.telejam.LongPollingBot
import io.github.ageofwar.telejam.TelegramException
import io.github.ageofwar.telejam.loggers.Loggers.emptyLogger
import io.github.ageofwar.telejam.messages.Message
import io.github.ageofwar.telejam.messages.MessageHandler
import java.io.IOException

class TelejamBot(configuration: BotConfiguration, private val logger: ConsoleLogger) : LongPollingBot(Bot.fromToken(configuration.botToken), emptyLogger()) {
    init {
        configuration.messageEvents.forEach {
            events.registerUpdateHandlers(MessageEventHandler(bot, it, logger))
        }
    }

    override fun run() {
        logger.log(getString("console.log.start", bot.username))
        super.run()
        logger.log(getString("console.log.stop", bot.username))
    }

    override fun onError(t: Throwable) {
        if (t is TelegramException) {
            logger.logError(getString(toErrorKey(t.errorCode), t.message ?: "Unknown error", t.errorCode.toString()))
        } else {
            logger.logError(t.message ?: "Unknown error (${t::class.qualifiedName})")
        }
    }
}

class MessageEventHandler(private val bot: Bot, private val event: Event<Message>, private val logger: ConsoleLogger) : MessageHandler {
    override fun onMessage(message: Message) {
        logger.logMessage(message)
        val (filter, action) = event
        if (filter.filter(message)) {
            action.run(bot, message, logger)
        }
    }
}

fun newTelejamBot(configuration: BotConfiguration, logger: ConsoleLogger): TelejamBot {
    return try {
        TelejamBot(configuration, logger)
    } catch (e: IOException) {
        if (e is TelegramException) {
            logger.logError(getString(toErrorKey(e.errorCode), e.message ?: "Unknown error", e.errorCode.toString()))
        } else {
            logger.logError(getString("console.log.unknownError", e.message ?: "Unknown error", e::class.qualifiedName ?: "Unknown"))
        }
        throw e
    }
}

fun toErrorKey(code: Int) = "console.log." + when (code) {
    401 -> "unauthorized"
    404 -> "notFound"
    else -> "unknownError"
}