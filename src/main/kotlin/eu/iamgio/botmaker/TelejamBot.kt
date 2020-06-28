package eu.iamgio.botmaker

import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.lib.BotConfiguration
import eu.iamgio.botmaker.lib.ConsoleLogger
import eu.iamgio.botmaker.lib.Event
import eu.iamgio.botmaker.lib.telejam.text
import io.github.ageofwar.telejam.Bot
import io.github.ageofwar.telejam.LongPollingBot
import io.github.ageofwar.telejam.messages.Message
import io.github.ageofwar.telejam.messages.MessageHandler

class TelejamBot(configuration: BotConfiguration, logger: ConsoleLogger) : LongPollingBot(Bot.fromToken(configuration.botToken)) {
    init {
        configuration.messageEvents.forEach {
            events.registerUpdateHandlers(MessageEventHandler(bot, it, logger))
        }
    }
}

class MessageEventHandler(private val bot: Bot, private val event: Event<Message>, private val logger: ConsoleLogger) : MessageHandler {
    override fun onMessage(message: Message) {
        logger.log(getString("event.MessageEventNode.log", message.text?.toString() ?: "", message.chat.title))
        val (filter, action) = event
        if (filter.filter(message)) {
            action.run(bot, message, logger)
        }
    }
}