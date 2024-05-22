package ru.practicum.android.diploma.filter.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.filter.data.dto.SearchIndustriesResponse
import ru.practicum.android.diploma.filter.domain.api.SearchIndustriesRepository
import ru.practicum.android.diploma.filter.domain.model.Industry
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.sharing.data.ResourceProvider
import ru.practicum.android.diploma.util.Constants
import ru.practicum.android.diploma.util.Resource

class SearchIndustriesRepositoryImpl(
    private val client: NetworkClient,
    private val resourceProvider: ResourceProvider,
) :
    SearchIndustriesRepository {
    override suspend fun searchIndustries(): Flow<Resource<List<Industry>>> = flow {
        val response = client.doRequestIndustries()
        when (response.resultCode) {
            Constants.CONNECTION_ERROR -> emit(handleConnectionError())
            Constants.SUCCESS -> emit(handleSuccessResponse(response as SearchIndustriesResponse))
            else -> emit(handleServerError())
        }
    }

    private fun handleConnectionError(): Resource.Error<List<Industry>> {
        return Resource.Error(resourceProvider.getErrorInternetConnection())
    }

    private fun handleServerError(): Resource.Error<List<Industry>> {
        return Resource.Error(resourceProvider.getErrorServer())
    }

    private fun handleSuccessResponse(response: SearchIndustriesResponse): Resource.Success<List<Industry>> {
        val industryDtoList = response.results
        val industryList = industryDtoList.map { industry ->
            Industry(name = industry.name)
        }
        return Resource.Success(industryList)
    }
}
