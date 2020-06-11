package eu.iamgio.botmaker

import eu.iamgio.botmaker.lib.BotConfiguration
import eu.iamgio.botmaker.lib.MessageEvent
import io.github.ageofwar.telejam.Bot
import io.github.ageofwar.telejam.LongPollingBot
import io.github.ageofwar.telejam.messages.Message
import io.github.ageofwar.telejam.messages.MessageHandler

class TelejamBot(configuration: BotConfiguration) : LongPollingBot(Bot.fromToken(configuration.botToken)) {
    init {
        configuration.messageEvents.forEach {
            events.registerUpdateHandlers(MessageEventHandler(bot, it))
        }
    }
}

class MessageEventHandler(private val bot: Bot, private val messageEvent: MessageEvent) : MessageHandler {
    override fun onMessage(message: Message) {
        val (filter, action) = messageEvent
        if (filter.filter(message)) {
            action.run(bot, message)
        }
    }
}