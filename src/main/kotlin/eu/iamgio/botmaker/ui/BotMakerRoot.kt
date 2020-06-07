package eu.iamgio.botmaker.ui

import javafx.scene.control.SplitPane
import javafx.scene.layout.AnchorPane

/**
 * @author Giorgio Garofalo
 */
class BotMakerRoot : AnchorPane() {

    private val splitPane = SplitPane().apply {
        bindSize(this@BotMakerRoot)
        setDividerPositions(0.25)
    }

    private val leftControl = SplitControl().apply {
        styleClass += "left-control"
    }

    private val rightControl = SplitControl().apply {
        styleClass += "right-control"
    }

    init {
        splitPane.items.addAll(leftControl, rightControl)
        children += splitPane

        val botList = BotListNode()
        botList.children.addAll(BotNameNode("my_bot_1"), BotNameNode("my_bot_2")) // Test
        leftControl.children += botList
    }
}