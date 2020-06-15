package eu.iamgio.botmaker.ui.botcontrol.event

import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.lib.Action
import eu.iamgio.botmaker.lib.Event
import eu.iamgio.botmaker.lib.EventComponent
import eu.iamgio.botmaker.lib.Filter
import eu.iamgio.botmaker.ui.bindSize
import eu.iamgio.botmaker.ui.botcontrol.BotControlPane
import eu.iamgio.botmaker.ui.createSvg
import eu.iamgio.botmaker.ui.withClass
import eu.iamgio.botmaker.ui.wrap
import javafx.beans.binding.Bindings
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.FlowPane
import javafx.scene.layout.VBox

/**
 * @author Giorgio Garofalo
 */
class EventNode<T>(event: Event<T>, eventNameKey: String, private val botControl: BotControlPane) : VBox() {

    private val actionsVBox = VBox().withClass("actions")

    init {
        styleClass += "event"
        bindSize(bindHeight = false)

        children += Label(getString("event.$eventNameKey") + ":").withClass("event-title")

        children += actionsVBox

        actionsVBox.children += Label("+ ${getString("new.action")}").withClass("new").apply {
            setOnMouseClicked {
                println("New action")
                // TODO new action
            }
        }

        addAction(event.filter)
        addAction(event.action)
    }

    fun toEvent(): Event<T> {
        val eventFlowPanes = actionsVBox.children.filterIsInstance<EventFlowPane<T>>()
        // TODO support multiple filters/actions
        return Event(
                eventFlowPanes.first { it.eventComponent is Filter }.toEventComponent() as Filter<T>,
                eventFlowPanes.first { it.eventComponent is Action }.toEventComponent() as Action<T>
        )
    }

    private fun addAction(eventComponent: EventComponent<T>) {
        actionsVBox.children.add(actionsVBox.children.size - 1, EventFlowPane(eventComponent))
    }

    inner class EventFlowPane<T>(val eventComponent: EventComponent<T>) : FlowPane() {

        private val graphics: Array<out EventComponent.EventComponentGraphics>

        init {
            hgap = 10.0
            graphics = eventComponent.toUI()

            graphics.forEach {
                children += when(it) {
                    is EventComponent.EventComponentText -> Label(getString(it.textKey)).withClass("event-action")
                    is EventComponent.EventComponentField -> TextField().also { textField ->
                        textField.text = it.content
                        it.textProperty.bind(textField.textProperty())

                        textField.focusedProperty().addListener { _, _, focused ->
                            if(!focused) botControl.autosave()
                        }
                    }
                    is EventComponent.EventComponentBooleanIcon -> createSvg().apply {
                        contentProperty().bind(
                                Bindings.`when`(it.selectedProperty)
                                        .then(it.svgOn)
                                        .otherwise(it.svgOff)
                        )

                        it.selectedProperty.set(it.initialState)
                        it.selectedProperty.addListener { _ -> botControl.autosave() }
                    }.wrap().apply {
                        minWidth = 30.0
                        setOnMouseClicked { _ -> it.selectedProperty.set(it.value.not()) }
                    }
                    else -> null
                }
            }
        }

        fun toEventComponent(): EventComponent<T> {
            return eventComponent.fromUI(graphics)
        }
    }
}