package eu.iamgio.botmaker.ui.botcontrol.event

import eu.iamgio.botmaker.lib.Actions
import eu.iamgio.botmaker.lib.EventSpecs
import eu.iamgio.botmaker.ui.botcontrol.BotControlPane
import eu.iamgio.botmaker.ui.withClass

fun <T> buildEventBlock(specs: EventSpecs<T>, botControlPane: BotControlPane, actions: Actions<T>, textKey: String): EventBlock<T> {
    val block = ActionEventBlock(specs, botControlPane, actions).withClass("sub-block")
    actions.actions.forEach { block.add(it, botControlPane, addToActions = false) }

    block.children.add(0, buildEventLine(botControlPane, text(textKey)).withClass("block-title").also {
        it.setOnRemove {
            // TODO remove block
        }
    })

    return block
}