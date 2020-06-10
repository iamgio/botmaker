package eu.iamgio.botmaker

import eu.iamgio.botmaker.bundle.ResourceBundle
import eu.iamgio.botmaker.bundle.getString
import eu.iamgio.botmaker.lib.BotConfiguration
import eu.iamgio.botmaker.ui.BotMakerRoot
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.text.Font
import javafx.stage.Stage

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
        val (locale) = Settings.loadOrDefault(SETTINGS_PATH, Settings())

        val bots = mutableListOf<BotConfiguration>() // TODO Load from workspace
        bots.add(BotConfiguration("my_bot_1", "abc", emptyList()))
        bots.add(BotConfiguration("my_bot_2", "def", emptyList()))

        root = BotMakerRoot(bots)

        ResourceBundle.load(locale)

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
}

fun main() {
    Application.launch(BotMaker::class.java)
}