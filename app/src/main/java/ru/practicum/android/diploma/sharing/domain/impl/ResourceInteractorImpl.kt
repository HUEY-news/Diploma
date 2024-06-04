package ru.practicum.android.diploma.sharing.domain.impl

import ru.practicum.android.diploma.sharing.data.ResourceProvider
import ru.practicum.android.diploma.sharing.domain.api.ResourceInteractor

class ResourceInteractorImpl(private val resourceProvider: ResourceProvider) : ResourceInteractor {
    override fun getErrorInternetConnection(): String {
        return resourceProvider.getErrorInternetConnection()
    }

    override fun getErrorEmptyListVacancy(): String {
        return resourceProvider.getErrorEmptyListVacancy()
    }

    override fun checkInternetConnection(): Boolean {
        return resourceProvider.checkInternetConnection()
    }

    override fun clearShared() {
        resourceProvider.clearShared()
    }

    override fun addToShared(editTextString: String) {
        resourceProvider.addToShared(editTextString)
    }

    override fun getShared(): String? {
        return resourceProvider.getShared()
    }
}
