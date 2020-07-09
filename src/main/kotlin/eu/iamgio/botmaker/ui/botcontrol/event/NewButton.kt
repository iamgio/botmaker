package eu.iamgio.botmaker.ui.botcontrol.event

import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.lib.Event
import eu.iamgio.botmaker.lib.EventSpecs
import eu.iamgio.botmaker.ui.botcontrol.BotControlPane
import eu.iamgio.botmaker.ui.center
import eu.iamgio.botmaker.ui.popup.EventChoicePopup
import io.github.ageofwar.telejam.messages.Message
import javafx.scene.control.Label

/**
 * @author Giorgio Garofalo
 */
open class NewButton(textKey: String) : Label("+ ${getString(textKey)}") {

    init {
        styleClass += "new"
    }
}

class NewEventButton(botControlPane: BotControlPane) : NewButton("new.event") {

    init {
        setOnMouseClicked {
            println("New event")
            val newEvent = Event<Message>() //TODO choice
            botControlPane.bot.messageEvents += newEvent
            botControlPane.eventsVBox.children += MessageEventNode(newEvent, botControlPane)
        }
    }
}

class NewFilterButton<T>(eventBlock: EventBlock<T>, specs: EventSpecs<T>, botControlPane: BotControlPane) : NewButton("new.filter") {

    init {
        setOnMouseClicked {
            println("New filter")
            val eventChoicePopup = EventChoicePopup(EventChoicePopup.ChoiceType.FILTER, specs.getAvailableFilters(), eventBlock, botControlPane)
            eventChoicePopup.center()
            eventChoicePopup.show()
        }
    }
}

class NewActionButton<T>(eventBlock: EventBlock<T>, specs: EventSpecs<T>, botControlPane: BotControlPane) : NewButton("new.action") {

    init {
        setOnMouseClicked {
            println("New filter")
            val eventChoicePopup = EventChoicePopup(EventChoicePopup.ChoiceType.ACTION, specs.getAvailableActions(), eventBlock, botControlPane)
            eventChoicePopup.center()
            eventChoicePopup.show()
        }
    }
}