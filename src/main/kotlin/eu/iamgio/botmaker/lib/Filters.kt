package eu.iamgio.botmaker.lib

import eu.iamgio.botmaker.lib.telejam.text
import eu.iamgio.botmaker.ui.SVG_CASE_SENSITIVE_OFF
import eu.iamgio.botmaker.ui.SVG_CASE_SENSITIVE_ON
import eu.iamgio.botmaker.ui.botcontrol.BotControlPane
import eu.iamgio.botmaker.ui.botcontrol.event.buildEventLine
import eu.iamgio.botmaker.ui.botcontrol.event.field
import eu.iamgio.botmaker.ui.botcontrol.event.icon
import eu.iamgio.botmaker.ui.botcontrol.event.text
import io.github.ageofwar.telejam.messages.Message
import io.github.ageofwar.telejam.messages.TextMessage
import javafx.scene.layout.Pane

data class Filters<T>(val filters: MutableList<Filter<T>> = mutableListOf()) : Filter<T> {
    override fun filter(event: T) = filters.all { it.filter(event) }

    override fun toNode(botControl: BotControlPane) = Pane()
}

data class IfMessageStartsWith(var text: String, var isCaseSensitive: Boolean) : Filter<Message> {
    override fun filter(event: Message): Boolean {
        return event.text?.startsWith(text, ignoreCase = !isCaseSensitive) ?: false
    }

    override fun toNode(botControl: BotControlPane) = buildEventLine(botControl,
            text("filter.IfMessageStartsWith.text"),
            field(text) {text = it},
            icon(SVG_CASE_SENSITIVE_OFF, SVG_CASE_SENSITIVE_ON, "filter.global.casesensitive", isCaseSensitive) {isCaseSensitive = it}
    )
}

data class IfMessageEndsWith(var text: String, var isCaseSensitive: Boolean) : Filter<Message> {
    override fun filter(event: Message): Boolean {
        return event.text?.endsWith(text, ignoreCase = !isCaseSensitive) ?: false
    }

    override fun toNode(botControl: BotControlPane) = buildEventLine(botControl,
            text("filter.IfMessageEndsWith.text"),
            field(text) {text = it},
            icon(SVG_CASE_SENSITIVE_OFF, SVG_CASE_SENSITIVE_ON, "filter.global.casesensitive", isCaseSensitive) {isCaseSensitive = it}
    )
}

data class IfMessageContains(var text: String, var isCaseSensitive: Boolean) : Filter<Message> {
    override fun filter(event: Message): Boolean {
        return event.text?.contains(text, ignoreCase = !isCaseSensitive) ?: false
    }

    override fun toNode(botControl: BotControlPane) = buildEventLine(botControl,
            text("filter.IfMessageContains.text"),
            field(text) {text = it},
            icon(SVG_CASE_SENSITIVE_OFF, SVG_CASE_SENSITIVE_ON, "filter.global.casesensitive", isCaseSensitive) {isCaseSensitive = it}
    )
}

data class IfMessageMatchesRegex(var text: String, var isCaseSensitive: Boolean) : Filter<Message> {
    override fun filter(event: Message): Boolean {
        val regexOptions = mutableSetOf(RegexOption.DOT_MATCHES_ALL)
        if (!isCaseSensitive) regexOptions += RegexOption.IGNORE_CASE
        return event.text?.matches(Regex(text, regexOptions)) ?: false
    }

    override fun toNode(botControl: BotControlPane) = buildEventLine(botControl,
            text("filter.IfMessageMatchesRegex.text"),
            field(text) {text = it},
            icon(SVG_CASE_SENSITIVE_OFF, SVG_CASE_SENSITIVE_ON, "filter.global.casesensitive", isCaseSensitive) {isCaseSensitive = it}
    )
}

data class IfMessageContainsRegex(var text: String, var isCaseSensitive: Boolean) : Filter<Message> {
    override fun filter(event: Message): Boolean {
        val regexOptions = mutableSetOf(RegexOption.DOT_MATCHES_ALL)
        if (!isCaseSensitive) regexOptions += RegexOption.IGNORE_CASE
        return event.text?.contains(Regex(text, regexOptions)) ?: false
    }

    override fun toNode(botControl: BotControlPane) = buildEventLine(botControl,
            text("filter.IfMessageContainsRegex.text"),
            field(text) {text = it},
            icon(SVG_CASE_SENSITIVE_OFF, SVG_CASE_SENSITIVE_ON, "filter.global.casesensitive", isCaseSensitive) {isCaseSensitive = it}
    )
}

data class IfMessageIsCommand(var text: String, var isCaseSensitive: Boolean) : Filter<Message> {
    override fun filter(event: Message): Boolean {
        return event is TextMessage && event.isCommand && event.text?.startsWith("/$text", ignoreCase = !isCaseSensitive) ?: false
    }

    override fun toNode(botControl: BotControlPane) = buildEventLine(botControl,
            text("filter.IfMessageIsCommand.text"),
            field(text) {text = it},
            icon(SVG_CASE_SENSITIVE_OFF, SVG_CASE_SENSITIVE_ON, "filter.global.casesensitive", isCaseSensitive) {isCaseSensitive = it}
    )
}