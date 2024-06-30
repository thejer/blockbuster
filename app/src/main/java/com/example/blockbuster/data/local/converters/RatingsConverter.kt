package com.example.blockbuster.data.local.converters

import androidx.room.TypeConverter
import com.example.blockbuster.data.local.entities.Rating
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RatingsConverter {

    @TypeConverter
    fun RatingToString(ratings: List<Rating>): String = Gson().toJson(ratings)

    @TypeConverter
    fun StringToRating(string: String): List<Rating> {
        val listType = object : TypeToken<List<Rating>>() {}.type
        return Gson().fromJson(string, listType)
    }
}