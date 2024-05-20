package ru.practicum.android.diploma.sharing.domain.impl

import ru.practicum.android.diploma.sharing.data.ExternalNavigator
import ru.practicum.android.diploma.sharing.domain.api.SharingInteractor

class SharingInteractorImpl(private val externalNavigator: ExternalNavigator) : SharingInteractor {
    override fun shareVacancy(url: String) {
        externalNavigator.shareLink(url)
    }

    override fun writeToEmployer(email: String) {
        externalNavigator.openEmail(email)
    }

    override fun callPhoneNumber(phone: String) {
        externalNavigator.callPhoneNumber(phone)
    }
}
