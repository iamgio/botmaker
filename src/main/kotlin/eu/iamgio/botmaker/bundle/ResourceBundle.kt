package eu.iamgio.botmaker.bundle

import java.util.*

private const val LANG_PATH = "/lang/lang_"
private const val EXTENSION = ".properties"

/**
 * @author Giorgio Garofalo
 */
object ResourceBundle {

    private val properties = Properties()

    fun load(locale: Locale) {
        properties.load(javaClass.getResourceAsStream(LANG_PATH + locale.language + EXTENSION))
    }

    operator fun get(key: String) = properties[key]?.toString() ?: "[$key]"
}

/**
 * Gets a string from the current resource bundle and replaces $1, $2, etc with given arguments
 */
fun getString(key: String, vararg replaces: String) = with(ResourceBundle[key]) {
    var string = this
    replaces.forEachIndexed { index, s -> string = string.replace("$${index + 1}", s) }
    string
}