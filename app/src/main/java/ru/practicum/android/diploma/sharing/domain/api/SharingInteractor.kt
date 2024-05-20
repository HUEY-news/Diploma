package ru.practicum.android.diploma.sharing.domain.api

interface SharingInteractor {
    fun shareVacancy(url: String)
    fun writeToEmployer(email: String)
    fun callPhoneNumber(phone: String)
}
