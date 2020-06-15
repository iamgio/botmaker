package eu.iamgio.botmaker.lib

import eu.iamgio.botmaker.ui.SVG_BELL_OFF
import eu.iamgio.botmaker.ui.SVG_BELL_RING
import eu.iamgio.botmaker.ui.SVG_REPLY
import eu.iamgio.botmaker.ui.SVG_REPLY_EMPTY
import io.github.ageofwar.telejam.Bot
import io.github.ageofwar.telejam.messages.Message
import io.github.ageofwar.telejam.methods.SendMessage

data class Actions<T>(val actions: List<Action<T>>) : Action<T> {
    override fun run(bot: Bot, event: T) = actions.forEach { it.run(bot, event) }
}

data class Reply(val text: String, val sendAsReply: Boolean = true, val notification: Boolean = true) : Action<Message> {
    override fun run(bot: Bot, event: Message) {
        val sendMessage = SendMessage()
                .text(text)
                .disableNotification(!notification)
        if (sendAsReply) {
            sendMessage.replyToMessage(event)
        } else {
            sendMessage.chat(event.chat)
        }
        bot.execute(sendMessage)
    }

    override fun toUI() = arrayOf(
            text("reply"),
            field(text, this::text),
            icon(SVG_REPLY_EMPTY, SVG_REPLY, sendAsReply, this::sendAsReply),
            icon(SVG_BELL_OFF, SVG_BELL_RING, notification, this::notification)
    )

    override fun fromUI(graphics: Array<out EventComponent.EventComponentGraphics>): EventComponent<Message> {
        return Reply(graphics.value<String>(this::text), graphics.value(this::sendAsReply), graphics.value(this::notification))
    }
}