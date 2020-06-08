package eu.iamgio.botmaker

import eu.iamgio.botmaker.bundle.ResourceBundle
import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.lib.gson.gson
import eu.iamgio.botmaker.ui.BotMakerRoot
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.text.Font
import javafx.stage.Stage
import java.io.File
import java.util.*

const val SCENE_WIDTH = 900.0
const val SCENE_HEIGHT = 600.0

const val SETTINGS_PATH = "./settings.json"

lateinit var root: BotMakerRoot
    private set

/**
 * @author Giorgio Garofalo
 */
class BotMaker : Application() {

    override fun start(primaryStage: Stage) {
        val (language) = Settings.loadOrDefault(SETTINGS_PATH, Settings())
        root = BotMakerRoot()

        ResourceBundle.load(language)

        loadFont("Karla-Regular.ttf")
        loadFont("Karla-Bold.ttf")

        val scene = Scene(root, SCENE_WIDTH, SCENE_HEIGHT)
        scene.stylesheets += "/css/style.css"

        root.prefWidthProperty().bind(scene.widthProperty())
        root.prefHeightProperty().bind(scene.heightProperty())

        primaryStage.scene = scene
        primaryStage.title = getString("title")
        primaryStage.isMaximized = true
        primaryStage.show()
    }

    private fun loadFont(name: String) = Font.loadFont(javaClass.getResourceAsStream("/font/$name"), 18.0)

    data class Settings(val language: Locale = Locale.ENGLISH) {
        fun save(path: String) {
            File(path).also { it.parentFile.mkdirs() }.writer().use {
                gson.toJson(this, it)
            }
        }

        companion object {
            fun load(path: String): Settings {
                return File(path).reader().use {
                    gson.fromJson(it, Settings::class.java)
                }
            }

            fun loadOrDefault(path: String, default: Settings): Settings {
                return if (File(path).exists()) load(path) else default.also { it.save(path) }
            }
        }
    }
}

fun main() {
    Application.launch(BotMaker::class.java)
}