package eu.iamgio.botmaker.lib.gson

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.util.*

object LocaleTypeAdapter : TypeAdapter<Locale>() {
    override fun write(writer: JsonWriter, locale: Locale?) {
        if (locale == null) {
            writer.nullValue()
            return
        }
        writer.value(locale.toLanguageTag())
    }

    override fun read(reader: JsonReader): Locale {
        return Locale.forLanguageTag(reader.nextString())
    }
}