package eu.iamgio.botmaker.ui

import javafx.css.PseudoClass
import javafx.scene.Node
import javafx.scene.input.KeyCode
import javafx.scene.layout.VBox

/**
 * VBox which can be browsed through arrow keys
 * @author Giorgio Garofalo
 */
open class BrowsableVBox : VBox() {

    private var index = -1

    private fun increase() {
        if(index >= children.size - 1) {
            index = 0
        } else {
            index++
        }
    }

    private fun decrease() {
        if(index <= 0) {
            index = children.size - 1
        } else {
            index--
        }
    }

    private fun Node.updateSelectedPseudoClass(active: Boolean) {
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), active)
    }

    init {
        isFocusTraversable = true
        setOnKeyPressed {
            when(it.code) {
                KeyCode.UP -> decrease()
                KeyCode.DOWN -> increase()
                else -> return@setOnKeyPressed
            }
            children.forEach { child -> child.updateSelectedPseudoClass(false) }
            val node = children[index]
            node.updateSelectedPseudoClass(true)
        }
        setOnKeyReleased {
            if(it.code == KeyCode.ENTER) {
                val node = children[index]
                if(node is Actionable) node.onAction()
            }
        }
    }
}

interface Actionable {

    fun onAction()
}