package ru.practicum.android.diploma.convertor

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.favorite.data.model.PhonesDto
import ru.practicum.android.diploma.favorite.domain.model.Phones

class TypeConverter {

    @TypeConverter
    fun stringListToJson(list: List<String>?): String? = Gson().toJson(list)

    @TypeConverter
    fun jsonToStringList(list: String?): List<String> {
        if (list == null || list.trim() == "null"){
            return listOf()
        }

        val typeToken = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(list, typeToken)
    }

    @TypeConverter
    fun phonesListToJson(list: List<PhonesDto>?): String? = Gson().toJson(list)

    @TypeConverter
    fun jsonToPhonesList(list: String?): List<PhonesDto> {
        if (list == null || list.trim() == "null"){
            return listOf()
        }

        val typeToken = object : TypeToken<List<PhonesDto>>() {}.type
        return Gson().fromJson(list, typeToken)
    }
}
