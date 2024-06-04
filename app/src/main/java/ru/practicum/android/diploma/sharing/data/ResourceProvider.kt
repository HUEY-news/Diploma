package ru.practicum.android.diploma.sharing.data

import android.content.Context
import android.content.SharedPreferences
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.util.CheckConnection

class ResourceProvider(
    private val sharedPreferences: SharedPreferences,
    val context: Context,
    private val checkConnection: CheckConnection
) {

    fun getAppName(): String {
        return context.getString(R.string.app_name)
    }

    fun getErrorInternetConnection(): String {
        return context.getString(R.string.no_internet)
    }

    fun getErrorEmptyListVacancy(): String {
        return context.getString(R.string.failed_to_get_list_of_vacancies)
    }

    fun getErrorServer(): String {
        return context.getString(R.string.server_error)
    }

    fun checkInternetConnection(): Boolean {
        return checkConnection.isInternetAvailable()
    }

    private companion object {
        const val EDIT_TEXT_KEY = "key_for_edit_text"
    }

    fun clearShared() {
        sharedPreferences.edit().clear().apply()
    }

    fun addToShared(editTextString: String) {
        sharedPreferences.edit()
            .putString(
                EDIT_TEXT_KEY,
                editTextString
            )
            .apply()
    }

    fun getShared(): String {
        val editTextString = sharedPreferences.getString(EDIT_TEXT_KEY, null)
        return editTextString.toString()
    }
}
