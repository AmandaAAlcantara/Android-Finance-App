package com.example.mob_dev_portfolio.db

import androidx.room.TypeConverter
import com.example.mob_dev_portfolio.models.Source


class Converters {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}