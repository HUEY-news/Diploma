package ru.practicum.android.diploma.sharing.data

import android.content.Context
import android.content.Intent
import android.net.Uri

class ExternalNavigator(val context: Context) {
    fun shareLink(link: String) {
        context.startActivity(Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, link)
            type = "text/plain"
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }

    fun openEmail(email: String) {
        val uri: Uri = Uri.parse("mailto:")
            .buildUpon()
            .appendQueryParameter("to", email)
            .build()
        val emailIntent =
            Intent(Intent.ACTION_SENDTO, uri)
        context.startActivity(
            Intent.createChooser(
                emailIntent,
                ""
            ).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
        )
    }

    fun callPhoneNumber(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.setData(Uri.parse(phone))
        context.startActivity(intent)
    }
}
