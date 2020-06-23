package eu.iamgio.botmaker.ui

import eu.iamgio.botmaker.root
import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.Parent
import javafx.scene.layout.Region

/**
 * Binds width and height of the node to root's
 */
fun Region.bindSize(region: Region = root, bindWidth: Boolean = true, bindHeight: Boolean = true) {
    if(bindWidth) prefWidthProperty().bind(region.prefWidthProperty())
    if(bindHeight) prefHeightProperty().bind(region.prefHeightProperty())
}

fun Region.center(region: Region = root, bindX: Boolean = true, bindY: Boolean = true) {
    // Using integer properties in order to avoid non-zero values which cause scroll panes to have blurry content
    val x = SimpleIntegerProperty().also {
        it.bind(region.prefWidthProperty().divide(2).subtract(widthProperty().divide(2)))
    }
    val y = SimpleIntegerProperty().also {
        it.bind(region.prefHeightProperty().divide(2).subtract(heightProperty().divide(2)))
    }
    if(bindX) translateXProperty().bind(x)
    if(bindY) translateYProperty().bind(y)
}

fun <T> T.withClass(styleClass: String): T where T : Parent {
    this.styleClass += styleClass
    return this
}