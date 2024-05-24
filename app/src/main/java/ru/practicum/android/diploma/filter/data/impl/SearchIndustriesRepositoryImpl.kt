package ru.practicum.android.diploma.filter.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.filter.data.model.IndustryDto
import ru.practicum.android.diploma.filter.domain.api.SearchIndustriesRepository
import ru.practicum.android.diploma.filter.domain.model.Industry
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.sharing.data.ResourceProvider
import ru.practicum.android.diploma.util.Resource

class SearchIndustriesRepositoryImpl(
    private val client: NetworkClient,
) :
    SearchIndustriesRepository {

    override suspend fun searchIndustries(): Flow<Resource<List<Industry>>> = flow {
        val response = client.doRequestIndustries()
        if (response.data != null) {
            emit(handleSuccessResponse(response.data))
        } else {
            response.message?.let { handleErrorResponse(it) }?.let { emit(it) }
        }
    }

    private fun handleErrorResponse(message: String): Resource<List<Industry>> {
        return Resource.Error(message)
    }

    private fun handleSuccessResponse(response: List<IndustryDto>): Resource.Success<List<Industry>> {
        val industryList = response.map { industry ->
            Industry(name = industry.name)
        }
        return Resource.Success(industryList)
    }
}
