package com.simonesolita.oiltracker.retrofit

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.util.*

class DateTypeAdapter : TypeAdapter<Date>() {

    override fun write(out: JsonWriter, value: Date?) {
        if (value != null) out.value(value.time) else out.nullValue()
    }

    override fun read(`in`: JsonReader): Date {
        return Date(`in`.nextLong())
    }
}