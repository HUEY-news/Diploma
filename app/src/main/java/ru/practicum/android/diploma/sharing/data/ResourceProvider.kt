package ru.practicum.android.diploma.sharing.data

import android.content.Context
import ru.practicum.android.diploma.R

class ResourceProvider(val context: Context) {
    fun getAppName(): String {
        return context.getString(R.string.app_name)
    }
}
