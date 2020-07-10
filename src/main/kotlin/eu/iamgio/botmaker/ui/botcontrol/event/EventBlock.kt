package eu.iamgio.botmaker.ui.botcontrol.event

import eu.iamgio.botmaker.lib.Action
import eu.iamgio.botmaker.lib.EventSpecs
import eu.iamgio.botmaker.lib.Filter
import eu.iamgio.botmaker.ui.botcontrol.BotControlPane
import javafx.scene.Node
import javafx.scene.layout.VBox

/**
 * @author Giorgio Garofalo
 */
abstract class EventBlock<T, R>(val items: MutableList<R>, private val linkedItem: R?) : VBox() {

    init {
        styleClass += "event-block"
    }

    fun addGraphically(item: R, botControlPane: BotControlPane) {
        children.add(children.size - 1, nodeFromItem(item).also {
            (it as? EventLine)?.setOnRemove {
                items -= item
                children -= it
                botControlPane.autosave()
            }
        })
    }

    protected abstract fun nodeFromItem(item: R): Node

    abstract fun addToList(item: R)
    abstract fun remove(item: R)

    fun removeFrom(parentBlock: EventBlock<*, R>) {
        if(linkedItem != null) parentBlock.remove(linkedItem)
    }
}

class FilterEventBlock<T>(specs: EventSpecs<T>, private val botControlPane: BotControlPane, private val filters: MutableList<Filter<T>>, linkedItem: Filter<T>?) : EventBlock<T, Filter<T>>(filters, linkedItem) {

    init {
        children += NewFilterButton(this, specs, botControlPane)
    }

    override fun nodeFromItem(item: Filter<T>) = item.toNode(botControlPane)

    override fun addToList(item: Filter<T>) {
        filters += item
    }

    override fun remove(item: Filter<T>) {
        filters -= item
    }
}

class ActionEventBlock<T>(specs: EventSpecs<T>, private val botControlPane: BotControlPane, private val actions: MutableList<Action<T>>, linkedItem: Action<T>?) : EventBlock<T, Action<T>>(actions, linkedItem) {

    init {
        children += NewActionButton(this, specs, botControlPane)
    }

    override fun nodeFromItem(item: Action<T>) = item.toNode(botControlPane)

    override fun addToList(item: Action<T>) {
        actions += item
    }

    override fun remove(item: Action<T>) {
        actions -= item
    }
}