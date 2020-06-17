package eu.iamgio.botmaker.lib

import eu.iamgio.botmaker.lib.telejam.text
import eu.iamgio.botmaker.ui.botcontrol.BotControlPane
import eu.iamgio.botmaker.ui.botcontrol.event.buildEventLine
import eu.iamgio.botmaker.ui.botcontrol.event.field
import eu.iamgio.botmaker.ui.botcontrol.event.text
import io.github.ageofwar.telejam.messages.Message

data class IfMessageStartsWith(var text: String) : Filter<Message> {
    override fun filter(event: Message): Boolean {
        return event.text?.startsWith(text) ?: false
    }

    override fun toNode(botControl: BotControlPane) = buildEventLine(botControl,
            text("filter.IfMessageStartsWith.ifstarts"),
            field(text) {text = it}
    )
}
