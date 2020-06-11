package eu.iamgio.botmaker.ui

import eu.iamgio.botmaker.root
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
    if(bindX) translateXProperty().bind(region.prefWidthProperty().divide(2).subtract(widthProperty().divide(2)))
    if(bindY) translateYProperty().bind(region.prefHeightProperty().divide(2).subtract(heightProperty().divide(2)))
}

fun <T> T.withClass(styleClass: String): T where T : Parent {
    this.styleClass += styleClass
    return this
}