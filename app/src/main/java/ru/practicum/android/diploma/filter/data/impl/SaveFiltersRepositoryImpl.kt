package ru.practicum.android.diploma.filter.data.impl

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.filter.domain.api.SaveFiltersRepository
import ru.practicum.android.diploma.filter.domain.model.FiltersSave
import ru.practicum.android.diploma.util.Constants

class SaveFiltersRepositoryImpl(
    private val sharedPrefs: SharedPreferences,
    private val gson: Gson,
) : SaveFiltersRepository {
    override fun getFilters(): FiltersSave {
        val json =
            sharedPrefs.getString(Constants.FILTRATION_KEY, null)
                ?: return FiltersSave("", "", "", false)
        return gson.fromJson(json, FiltersSave::class.java)
    }

    override fun saveFilters(filters: FiltersSave) {
        val json = gson.toJson(filters)
        sharedPrefs.edit()
            .clear()
            .putString(Constants.FILTRATION_KEY, json)
            .apply()
    }

    override fun cleanFilters() {
        sharedPrefs.edit()
            .clear()
            .apply()
    }
}
