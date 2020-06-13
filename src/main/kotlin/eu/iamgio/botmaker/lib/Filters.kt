package eu.iamgio.botmaker.lib

import eu.iamgio.botmaker.lib.telejam.text
import io.github.ageofwar.telejam.messages.Message

data class IfMessageStartsWith(val text: String) : Filter<Message> {
    override fun filter(event: Message): Boolean {
        return event.text?.startsWith(text) ?: false
    }

    override fun toUI() = arrayOf(text("ifstarts"), field())
}
