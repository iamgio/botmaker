package eu.iamgio.botmaker.ui.botcontrol.event

import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.lib.Event
import eu.iamgio.botmaker.lib.EventSpecs
import eu.iamgio.botmaker.lib.MessageEventSpecs
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

    private val filtersBlock = FilterEventBlock(getSpecs(), botControlPane, event.filters.filters, null).withClass("filters")
    private val actionsBlock = ActionEventBlock(getSpecs(), botControlPane, event.actions.actions, null).withClass("actions")

    init {
        styleClass += "event"
        bindSize(bindHeight = false)

        children += EventNodeTitle()
        children += filtersBlock
        children += actionsBlock

        event.filters.filters.forEach {
            filtersBlock.addGraphically(it, botControlPane)
        }

        event.actions.actions.forEach {
            actionsBlock.addGraphically(it, botControlPane)
        }
    }

    abstract fun getSpecs(): EventSpecs<T>
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

    override fun getSpecs() = MessageEventSpecs()

    override fun removeEvent() {
        botControlPane.bot.messageEvents -= event
    }
}