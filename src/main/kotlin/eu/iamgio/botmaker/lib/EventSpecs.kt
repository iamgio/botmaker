package eu.iamgio.botmaker.lib

import io.github.ageofwar.telejam.messages.Message

/**
 * @author Giorgio Garofalo
 */
interface EventSpecs<T> {
    fun getAvailableFilters(): List<Filter<T>>
    fun getAvailableActions(): List<Action<T>>
}

class MessageEventSpecs : EventSpecs<Message> {

    override fun getAvailableFilters() = listOf(
            IfMessageIsCommand("", true),
            IfMessageStartsWith("", false),
            IfMessageEndsWith("", false),
            IfMessageContains("", false),
            IfMessageMatchesRegex("", false),
            IfMessageContainsRegex("", false)
    )

    override fun getAvailableActions() = listOf(
            Reply(""),
            MessageRandomAction()
    )
}