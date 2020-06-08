package eu.iamgio.botmaker.lib.gson

import com.google.gson.GsonBuilder
import eu.iamgio.botmaker.lib.Action
import eu.iamgio.botmaker.lib.Actions
import eu.iamgio.botmaker.lib.Filter
import eu.iamgio.botmaker.lib.Filters
import java.util.*

internal val gson = GsonBuilder()
        .registerTypeAdapter(Locale::class.java, LocaleTypeAdapter)
        .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(Filter::class.java, "type")
                .registerSubtype(Filters::class.java, "filters"))
        .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(Action::class.java, "type")
                .registerSubtype(Actions::class.java, "actions"))
        .create()