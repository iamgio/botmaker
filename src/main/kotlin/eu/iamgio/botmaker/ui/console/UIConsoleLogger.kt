package eu.iamgio.botmaker.ui.console

import eu.iamgio.botmaker.lib.ConsoleLogger
import eu.iamgio.botmaker.ui.withClass
import javafx.application.Platform
import javafx.scene.control.Label

/**
 * @author Giorgio Garofalo
 */
class UIConsoleLogger(private val console: ConsoleNode) : ConsoleLogger {

    override fun log(text: String) {
        Platform.runLater { console.children += Label(text).withClass("log") }
    }

    override fun logError(text: String) {
        Platform.runLater { console.children += Label(text).withClass("log").withClass("log-error") }
    }
}