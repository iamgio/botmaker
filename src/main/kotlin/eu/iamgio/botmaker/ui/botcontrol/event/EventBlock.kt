package eu.iamgio.botmaker.ui.botcontrol.event

import eu.iamgio.botmaker.lib.Action
import eu.iamgio.botmaker.lib.Actions
import eu.iamgio.botmaker.lib.Filter
import eu.iamgio.botmaker.lib.Filters
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

class FilterEventBlock<T>(eventNode: EventNode<T>, private val filters: Filters<T>) : EventBlock<T>() {

    init {
        children += NewFilterButton(this, eventNode)
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

class ActionEventBlock<T>(eventNode: EventNode<T>, private val actions: Actions<T>) : EventBlock<T>() {

    init {
        children += NewActionButton(this, eventNode)
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