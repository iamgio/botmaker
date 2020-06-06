package eu.iamgio.botmaker.ui

import eu.iamgio.botmaker.root
import javafx.scene.layout.Region

/**
 * Binds width and height of the node to root's
 */
fun Region.bindSize(region: Region = root, bindWidth: Boolean = true, bindHeight: Boolean = true) {
    if(bindWidth) prefWidthProperty().bind(region.prefWidthProperty())
    if(bindHeight) prefHeightProperty().bind(region.prefHeightProperty())
}