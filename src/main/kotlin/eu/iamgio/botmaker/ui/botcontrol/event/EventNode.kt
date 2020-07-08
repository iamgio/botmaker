package eu.iamgio.botmaker.ui.botcontrol.event

import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.lib.*
import eu.iamgio.botmaker.ui.*
import eu.iamgio.botmaker.ui.botcontrol.BotControlPane
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

    private val filtersBlock = FilterEventBlock(this, event.filters).withClass("filters")
    private val actionsBlock = ActionEventBlock(this, event.actions).withClass("actions")

    init {
        styleClass += "event"
        bindSize(bindHeight = false)

        children += EventNodeTitle()
        children += filtersBlock
        children += actionsBlock

        event.filters.filters.forEach {
            filtersBlock.add(it, botControlPane, addToFilters = false)
        }

        event.actions.actions.forEach {
            actionsBlock.add(it, botControlPane, addToActions = false)
        }
    }

    abstract fun getAvailableFilters(): List<Filter<T>>
    abstract fun getAvailableActions(): List<Action<T>>
    abstract fun removeEvent()

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