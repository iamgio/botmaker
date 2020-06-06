package eu.iamgio.botmaker

import eu.iamgio.botmaker.ui.BotMakerRoot
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.text.Font
import javafx.stage.Stage

const val SCENE_WIDTH = 900.0
const val SCENE_HEIGHT = 600.0

lateinit var root: BotMakerRoot
    private set

/**
 * @author Giorgio Garofalo
 */
class BotMaker : Application() {

    override fun start(primaryStage: Stage) {
        root = BotMakerRoot()

        loadFont("Karla-Regular.ttf")
        loadFont("Karla-Bold.ttf")

        val scene = Scene(root, SCENE_WIDTH, SCENE_HEIGHT)
        scene.stylesheets += "/css/style.css"

        root.prefWidthProperty().bind(scene.widthProperty())
        root.prefHeightProperty().bind(scene.heightProperty())

        primaryStage.scene = scene
        primaryStage.title = "BotMaker" // TODO bundle
        primaryStage.show()
    }

    private fun loadFont(name: String) = Font.loadFont("/font/$name", 18.0)
}

fun main() {
    Application.launch(BotMaker::class.java)
}