package eu.iamgio.botmaker.ui.botcontrol.event

import eu.iamgio.botmaker.lib.*
import eu.iamgio.botmaker.ui.botcontrol.BotControlPane
import eu.iamgio.botmaker.ui.withClass

fun <T, R> buildEventBlock(block: EventBlock<T, R>, botControlPane: BotControlPane, textKey: String): EventBlock<T, R> {
    block.styleClass += "sub-block"
    block.items.forEach { block.addGraphically(it, botControlPane) }

    block.children.add(0, buildEventLine(botControlPane, text(textKey)).withClass("block-title").also {
        it.setOnRemove {
            @Suppress("UNCHECKED_CAST")
            val parentBlock = block.parent as? EventBlock<*, R> ?: return@setOnRemove
            block.removeFrom(parentBlock)
            parentBlock.children -= block
            botControlPane.autosave()
        }
    })

    return block
}

fun <T> buildActionBlock(specs: EventSpecs<T>, botControlPane: BotControlPane, action: Action<T>, actions: Actions<T>, textKey: String)
    = buildEventBlock(ActionEventBlock(specs, botControlPane, actions.actions, action), botControlPane, textKey)

fun <T> buildFilterBlock(specs: EventSpecs<T>, botControlPane: BotControlPane, filter: Filter<T>, filters: Filters<T>, textKey: String)
    = buildEventBlock(FilterEventBlock(specs, botControlPane, filters.filters, filter), botControlPane, textKey)