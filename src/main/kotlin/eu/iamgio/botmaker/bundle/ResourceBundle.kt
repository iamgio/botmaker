package eu.iamgio.botmaker.bundle

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.internal.Streams
import com.google.gson.stream.JsonReader
import eu.iamgio.botmaker.lib.gson.gson
import java.util.*

private const val LANG_PATH = "/lang/lang_"
private const val EXTENSION = ".json"

/**
 * @author Giorgio Garofalo
 */
object ResourceBundle {

    private var json: JsonObject = JsonObject()

    fun load(locale: Locale) {
        json = Streams.parse(JsonReader(javaClass.getResourceAsStream(LANG_PATH + locale.language + EXTENSION).reader())).asJsonObject
    }

    operator fun get(key: String, jsonObject: JsonObject = json): String {
        val index = key.indexOf('.')
        return try {
            if (index == -1) {
                jsonObject[key].asString
            } else {
                val branch = key.substring(0, index)
                get(key.substring(index + 1), jsonObject[branch].asJsonObject)
            }
        } catch (e: Exception) {
            "[$key]"
        }
    }
}

/**
 * Gets a string from the current resource bundle and replaces $1, $2, etc with given arguments
 */
fun getString(key: String, vararg replaces: String) = with(ResourceBundle[key]) {
    var string = this
    replaces.forEachIndexed { index, s -> string = string.replace("$${index + 1}", s) }
    string
}