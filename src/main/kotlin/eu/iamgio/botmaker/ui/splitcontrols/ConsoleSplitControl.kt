package eu.iamgio.botmaker.ui.splitcontrols

import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.lib.BotConfiguration
import eu.iamgio.botmaker.root
import eu.iamgio.botmaker.ui.*
import eu.iamgio.botmaker.ui.console.ConsoleNode
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.image.Image
import javafx.scene.layout.VBox
import javafx.stage.Stage

/**
 * @author Giorgio Garofalo
 */
class ConsoleSplitControl() : SplitControl() {

    private val console = ConsoleNode()

    private val joinedProperty = SimpleBooleanProperty(true)
    private var stage: Stage? = null

    init {
        styleClass += "console-control"

        // Separate/join
        navBar.children += createSvg().apply {
            contentProperty().bind(
                    Bindings.`when`(joinedProperty)
                            .then(SVG_WINDOW_RESTORE)
                            .otherwise(SVG_WINDOW_MAXIMIZE)
            )
        }.wrap().apply {
            setOnMouseClicked {
                if(joinedProperty.value) {
                    separate()
                } else {
                    join()
                }
            }
        }

        // Run/stop
        navBar.children += createSvg().apply {
            contentProperty().bind(
                    Bindings.`when`(console.runningProperty)
                            .then(SVG_STOP)
                            .otherwise(SVG_PLAY)
            )
        }.wrap().apply {
            setOnMouseClicked {
                if(console.runningProperty.value) {
                    console.stop()
                } else {
                    console.run()
                }
            }
        }
    }

    fun runBot(bot: BotConfiguration) {
        console.bot = bot
        console.run()
        children += ScrollPane(console.also { it.bindSize(this, bindWidth = false) })
    }

    fun log(logType: LogType, text: String) {
        console.children += logType.toLabel(text)
    }

    private fun separate() {
        if(stage != null) {
            stage!!.requestFocus()
            return
        }
        joinedProperty.set(false)
        root.removeConsole()
        stage = Stage().also {
            it.scene = Scene(VBox(this).apply { styleClass += "console-root" }, 500.0, 400.0)
                    .apply { stylesheets += "/css/style.css" }
            it.setOnCloseRequest { join() }
            it.title = getString("console.title")
            it.icons += Image(javaClass.getResourceAsStream("/images/icon.png"))
            it.show()
        }
    }

    private fun join() {
        joinedProperty.set(true)
        stage?.close()
        stage = null
        root.addConsole(this)
    }
}

enum class LogType(private val styleClass: String) {

    STANDARD("log-standard"), ERROR("log-error");

    fun toLabel(text: String) = Label(text).withClass("log").withClass(styleClass)
}