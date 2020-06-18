package eu.iamgio.botmaker.ui.botcontrol.event

import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.lib.*
import eu.iamgio.botmaker.ui.bindSize
import eu.iamgio.botmaker.ui.botcontrol.BotControlPane
import eu.iamgio.botmaker.ui.withClass
import io.github.ageofwar.telejam.messages.Message
import javafx.scene.control.Label
import javafx.scene.layout.VBox

/**
 * @author Giorgio Garofalo
 */
open class EventNode<T>(event: Event<T>, private val botControlPane: BotControlPane) : VBox() {

    private val filtersNode = VBox().withClass("filters")
    private val actionsNode = VBox().withClass("actions")

    init {
        styleClass += "event"
        bindSize(bindHeight = false)

        children += Label(getString("event.${javaClass.simpleName}") + ":").withClass("event-title")
        children += filtersNode
        children += actionsNode

        filtersNode.children += Label("+ ${getString("new.filter")}").withClass("new").apply {
            setOnMouseClicked {
                println("New filter")
                val newFilter = IfMessageStartsWith("") as Filter<T> //TODO choice
                event.filters.filters += newFilter
                addFilter(newFilter)
            }
        }

        actionsNode.children += Label("+ ${getString("new.action")}").withClass("new").apply { //TODO move
            setOnMouseClicked {
                println("New action")
                val newAction = Reply("") as Action<T> //TODO choice
                event.actions.actions += newAction
                addAction(newAction)
            }
        }

        event.filters.filters.forEach {
            addFilter(it)
        }

        event.actions.actions.forEach {
            addAction(it)
        }
    }

    private fun addFilter(filter: Filter<T>) {
        filtersNode.children.add(filtersNode.children.size - 1, filter.toNode(botControlPane))
    }

    private fun addAction(action: Action<T>) {
        actionsNode.children.add(actionsNode.children.size - 1, action.toNode(botControlPane))
    }
}

class MessageEventNode(event: Event<Message>, botControlPane: BotControlPane) : EventNode<Message>(event, botControlPane)