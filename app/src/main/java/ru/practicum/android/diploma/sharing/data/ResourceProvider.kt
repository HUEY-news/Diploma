package ru.practicum.android.diploma.sharing.data

import android.content.Context
import ru.practicum.android.diploma.R

class ResourceProvider(val context: Context) {
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
}
