package eu.iamgio.botmaker.ui.botcontrol.event

import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.ui.botcontrol.BotControlPane
import eu.iamgio.botmaker.ui.createSvg
import eu.iamgio.botmaker.ui.withClass
import eu.iamgio.botmaker.ui.wrap
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Label
import javafx.scene.control.TextField

fun buildEventLine(botControl: BotControlPane, vararg graphics: EventComponentGraphics): EventLine {
    val eventLine = EventLine()
    with(eventLine) {

        graphics.forEach {
            children += when(it) {
                is EventComponentText -> Label(getString(it.textKey)).withClass("event-action")
                is EventComponentField -> TextField().also { textField ->
                    textField.text = it.content
                    it.textProperty.bind(textField.textProperty())

                    textField.focusedProperty().addListener { _, _, focused ->
                        if(!focused) {
                            it.set(it.value)
                            botControl.autosave()
                        }
                    }
                }
                is EventComponentBooleanIcon -> createSvg().apply {
                    contentProperty().bind(
                            Bindings.`when`(it.selectedProperty)
                                    .then(it.svgOn)
                                    .otherwise(it.svgOff)
                    )

                    it.selectedProperty.set(it.initialState)
                    it.selectedProperty.addListener { _ ->
                        it.set(it.value)
                        botControl.autosave()
                    }
                }.wrap().apply {
                    minWidth = 30.0
                    setOnMouseClicked { _ -> it.selectedProperty.set(it.value.not()) }

                    setOnMouseEntered { _ ->
                        hintLabel.text = getString(it.hintTextKey)
                    }
                    setOnMouseExited {
                        hintLabel.text = ""
                    }
                }
                else -> null
            }
        }
        children += hintLabel
    }
    return eventLine
}

// Classes that allow the component to be displayed on the UI
open class EventComponentGraphics
abstract class EventComponentGraphicsControl<T>(val set: (T) -> Unit) : EventComponentGraphics() {
    abstract val value: T
}

class EventComponentText(val textKey: String) : EventComponentGraphics()
class EventComponentField(val content: String, set: (String) -> Unit) : EventComponentGraphicsControl<String>(set) {
    val textProperty = SimpleStringProperty()
    override val value: String
        get() = textProperty.value
}

class EventComponentBooleanIcon(val svgOff: String, val svgOn: String, val hintTextKey: String, val initialState: Boolean, set: (Boolean) -> Unit) : EventComponentGraphicsControl<Boolean>(set) {
    val selectedProperty = SimpleBooleanProperty()
    override val value: Boolean
        get() = selectedProperty.value
}

fun text(textKey: String) = EventComponentText("event.$textKey")
fun field(content: String, set: (String) -> Unit) = EventComponentField(content, set)
fun icon(svgOff: String, svgOn: String, hintTextKey: String, initialState: Boolean, set: (Boolean) -> Unit) = EventComponentBooleanIcon(svgOff, svgOn, "event.$hintTextKey", initialState, set)