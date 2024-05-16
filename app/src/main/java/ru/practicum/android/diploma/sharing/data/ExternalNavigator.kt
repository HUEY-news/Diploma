package ru.practicum.android.diploma.sharing.data

import android.content.Context
import android.content.Intent

class ExternalNavigator(val context: Context) {
    fun shareLink(link: String) {
        context.startActivity(Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, link)
            type = "text/plain"
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }
}
