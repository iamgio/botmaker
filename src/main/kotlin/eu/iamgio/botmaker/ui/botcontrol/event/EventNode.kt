package eu.iamgio.botmaker.ui.botcontrol.event

import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.lib.*
import eu.iamgio.botmaker.ui.*
import eu.iamgio.botmaker.ui.botcontrol.BotControlPane
import eu.iamgio.botmaker.ui.popup.EventChoicePopup
import io.github.ageofwar.telejam.messages.Message
import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

/**
 * @author Giorgio Garofalo
 */
abstract class EventNode<T>(val event: Event<T>, val botControlPane: BotControlPane) : VBox() {

    private val filtersNode = VBox().withClass("filters")
    private val actionsNode = VBox().withClass("actions")

    init {
        styleClass += "event"
        bindSize(bindHeight = false)

        children += EventNodeTitle()
        children += filtersNode
        children += actionsNode

        filtersNode.children += Label("+ ${getString("new.filter")}").withClass("new").apply {
            setOnMouseClicked {
                println("New filter")
                val eventChoicePopup = EventChoicePopup(EventChoicePopup.ChoiceType.FILTER, getAvailableFilters(), this@EventNode)
                eventChoicePopup.center()
                eventChoicePopup.show()
            }
        }

        actionsNode.children += Label("+ ${getString("new.action")}").withClass("new").apply {
            setOnMouseClicked {
                println("New action")
                val eventChoicePopup = EventChoicePopup(EventChoicePopup.ChoiceType.ACTION, getAvailableActions(), this@EventNode)
                eventChoicePopup.center()
                eventChoicePopup.show()
            }
        }

        event.filters.filters.forEach {
            addFilter(it)
        }

        event.actions.actions.forEach {
            addAction(it)
        }
    }

    abstract fun getAvailableFilters(): List<Filter<T>>
    abstract fun getAvailableActions(): List<Action<T>>
    abstract fun removeEvent()

    fun addFilter(filter: Filter<T>) {
        @Suppress("DuplicatedCode")
        filtersNode.children.add(filtersNode.children.size - 1, filter.toNode(botControlPane).also {
            if(it is EventLine) {
                it.setOnRemove {
                    event.filters.filters -= filter
                    filtersNode.children -= it
                    botControlPane.autosave()
                }
            }
        })
    }

    fun addAction(action: Action<T>) {
        @Suppress("DuplicatedCode")
        actionsNode.children.add(actionsNode.children.size - 1, action.toNode(botControlPane).also {
            if(it is EventLine) {
                it.setOnRemove {
                    event.actions.actions -= action
                    actionsNode.children -= it
                    botControlPane.autosave()
                }
            }
        })
    }

    private inner class EventNodeTitle : HBox() {

        init {
            styleClass += "event-title-node"
            alignment = Pos.CENTER_LEFT

            children += Label(getString("event.${this@EventNode.javaClass.simpleName}.text") + ":").withClass("event-title")

            children += createSvg(SVG_CLOSE).wrap().apply {
                Platform.runLater {
                    setOnMouseClicked {
                        removeEvent()
                        botControlPane.eventsVBox.children -= this@EventNode
                        botControlPane.autosave()
                    }
                }
            }.withClass("remove-line-button")
        }
    }
}

class MessageEventNode(event: Event<Message>, botControlPane: BotControlPane) : EventNode<Message>(event, botControlPane) {
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
            RandomAction<Message>()
    )

    override fun removeEvent() {
        botControlPane.bot.messageEvents -= event
    }
}