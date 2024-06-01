package ru.practicum.android.diploma.sharing.domain.api

interface ResourceInteractor {
    fun getErrorInternetConnection(): String
    fun getErrorEmptyListVacancy(): String
    fun checkInternetConnection(): Boolean
}
