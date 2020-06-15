package eu.iamgio.botmaker.lib

import io.github.ageofwar.telejam.Bot
import io.github.ageofwar.telejam.messages.Message
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
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
    abstract class EventComponentGraphicsControl<T>(val property: KProperty<T>) : EventComponentGraphics() {
        abstract val value: T
    }

    class EventComponentText(val textKey: String) : EventComponentGraphics()
    class EventComponentField(val content: String, property: KProperty<String>) : EventComponentGraphicsControl<String>(property) {
        val textProperty = SimpleStringProperty()
        override val value: String
            get() = textProperty.value
    }
    class EventComponentBooleanIcon(val svgOff: String, val svgOn: String, val initialState: Boolean, property: KProperty<Boolean>) : EventComponentGraphicsControl<Boolean>(property) {
        val selectedProperty = SimpleBooleanProperty()
        override val value: Boolean
            get() = selectedProperty.value
    }

    fun text(textKey: String) = EventComponentText("event.action." + javaClass.simpleName + "." + textKey)
    fun field(content: String, property: KProperty<String>) = EventComponentField(content, property)
    fun icon(svgOff: String, svgOn: String, initialState: Boolean, property: KProperty<Boolean>) = EventComponentBooleanIcon(svgOff, svgOn, initialState, property)

    fun toUI(): Array<out EventComponentGraphics> = emptyArray()
    fun fromUI(graphics: Array<out EventComponentGraphics>): EventComponent<T> = this

    fun <T> Array<out EventComponentGraphics>.value(property: KProperty<T>): T =
            this.filterIsInstance<EventComponentGraphicsControl<T>>().first { it.property == property }.value
}
