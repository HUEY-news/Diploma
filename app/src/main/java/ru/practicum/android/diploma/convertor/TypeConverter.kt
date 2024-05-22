package ru.practicum.android.diploma.convertor

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.details.domain.model.Contacts
import ru.practicum.android.diploma.details.domain.model.Employer
import ru.practicum.android.diploma.details.domain.model.Salary

class TypeConverter {

    @TypeConverter
    fun stringListToJson(list: List<String>?): String? = Gson().toJson(list)

    @TypeConverter
    fun jsonToStringList(json: String?): List<String> {
        if (json == null || json.trim() == "null") {
            return listOf()
        }

        val typeToken = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(json, typeToken)
    }

    @TypeConverter
    fun contactsToJson(contacts: Contacts): String = Gson().toJson(contacts)

    @TypeConverter
    fun jsonToContacts(json: String): Contacts {
        val typeToken = object : TypeToken<Contacts>() {}.type
        return Gson().fromJson(json, typeToken)
    }

    @TypeConverter
    fun employerToJson(employer: Employer): String = Gson().toJson(employer)

    @TypeConverter
    fun jsonToEmployer(json: String): Employer {
        val typeToken = object : TypeToken<Employer>() {}.type
        return Gson().fromJson(json, typeToken)
    }

    @TypeConverter
    fun salaryToJson(salary: Salary): String = Gson().toJson(salary)

    @TypeConverter
    fun jsonToSalary(json: String): Salary {
        val typeToken = object : TypeToken<Salary>() {}.type
        return Gson().fromJson(json, typeToken)
    }
}
