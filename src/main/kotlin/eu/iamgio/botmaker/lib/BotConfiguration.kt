package eu.iamgio.botmaker.lib

import io.github.ageofwar.telejam.Bot
import io.github.ageofwar.telejam.messages.Message
import javafx.scene.control.TextField
import kotlin.reflect.KProperty

data class BotConfiguration(val botToken: String, val messageEvents: List<Event<Message>> = emptyList())

data class Event<T>(val filter: Filter<T>, val action: Action<T>)

interface Filter<T> : EventComponent<T> {
    fun filter(event: T): Boolean
}

interface Action<T> : EventComponent<T> {
    fun run(bot: Bot, event: T)
}

interface EventComponent<T> {
    // Classes that allow the component to be displayed on the UI
    open class EventComponentGraphics
    class EventComponentText(val textKey: String) : EventComponentGraphics()
    class EventComponentField(val content: String, val property: KProperty<*>) : EventComponentGraphics() {
        lateinit var textField: TextField
        val text: String
            get() = textField.text
    }

    fun text(textKey: String) = EventComponentText("event.action." + javaClass.simpleName + "." + textKey)
    fun field(content: String, property: KProperty<*>) = EventComponentField(content, property)

    fun toUI(): Array<out EventComponentGraphics> = emptyArray()
    fun fromUI(graphics: Array<out EventComponentGraphics>): EventComponent<T> = this

    fun Array<out EventComponentGraphics>.getValueFromProperty(property: KProperty<*>) =
            this.filterIsInstance<EventComponentField>().first { it.property == property }.text
}
