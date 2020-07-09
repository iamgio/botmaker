package eu.iamgio.botmaker.ui.botcontrol.event

import eu.iamgio.botmaker.lib.*
import eu.iamgio.botmaker.ui.botcontrol.BotControlPane
import javafx.scene.layout.VBox

/**
 * @author Giorgio Garofalo
 */
open class EventBlock<T> : VBox() {

    init {
        styleClass += "event-block"
    }
}

class FilterEventBlock<T>(specs: EventSpecs<T>, botControlPane: BotControlPane, private val filters: Filters<T>) : EventBlock<T>() {

    init {
        children += NewFilterButton(this, specs, botControlPane)
    }

    fun add(filter: Filter<T>, botControlPane: BotControlPane, addToFilters: Boolean = true) {
        if(addToFilters) filters.filters += filter

        children.add(children.size - 1, filter.toNode(botControlPane).also {
            (it as? EventLine)?.setOnRemove {
                filters.filters -= filter
                children -= it
                botControlPane.autosave()
            }
        })
    }
}

class ActionEventBlock<T>(specs: EventSpecs<T>, botControlPane: BotControlPane, private val actions: Actions<T>) : EventBlock<T>() {

    init {
        children += NewActionButton(this, specs, botControlPane)
    }

    fun add(action: Action<T>, botControlPane: BotControlPane, addToActions: Boolean = true) {
        if(addToActions) actions.actions += action

        children.add(children.size - 1, action.toNode(botControlPane).also {
            (it as? EventLine)?.setOnRemove {
                actions.actions -= action
                children -= it
                botControlPane.autosave()
            }
        })
    }
}