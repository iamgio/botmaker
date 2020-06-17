package eu.iamgio.botmaker.ui.botcontrol.event

import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.lib.Action
import eu.iamgio.botmaker.lib.Event
import eu.iamgio.botmaker.lib.Filter
import eu.iamgio.botmaker.ui.bindSize
import eu.iamgio.botmaker.ui.botcontrol.BotControlPane
import eu.iamgio.botmaker.ui.withClass
import io.github.ageofwar.telejam.messages.Message
import javafx.scene.control.Label
import javafx.scene.layout.VBox

/**
 * @author Giorgio Garofalo
 */
abstract class EventNode<T>(event: Event<T>, private val botControlPane: BotControlPane) : VBox() {

    private val filtersNode = VBox().withClass("filters")
    private val actionsNode = VBox().withClass("actions")

    init {
        styleClass += "event"
        bindSize(bindHeight = false)

        children += Label(getString("event.${javaClass.simpleName}") + ":").withClass("event-title")
        children += filtersNode
        children += actionsNode

        filtersNode.children += Label("+ ${getString("new.filter")}").withClass("new").apply { //TODO move
            setOnMouseClicked {
                println("New filter")
                // TODO new filter
            }
        }

        actionsNode.children += Label("+ ${getString("new.action")}").withClass("new").apply { //TODO move
            setOnMouseClicked {
                println("New action")
                // TODO new filter
            }
        }

        addFilter(event.filter)
        addAction(event.action)
    }

    private fun addFilter(filter: Filter<T>) {
        filtersNode.children.add(filtersNode.children.size - 1, filter.toNode(botControlPane))
    }

    private fun addAction(action: Action<T>) {
        actionsNode.children.add(actionsNode.children.size - 1, action.toNode(botControlPane))
    }
}

class MessageEventNode(event: Event<Message>, botControlPane: BotControlPane) : EventNode<Message>(event, botControlPane)