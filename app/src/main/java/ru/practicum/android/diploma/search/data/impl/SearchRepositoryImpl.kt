package ru.practicum.android.diploma.search.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.search.data.dto.SearchRequest
import ru.practicum.android.diploma.search.data.dto.SearchResponse
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.model.Employer
import ru.practicum.android.diploma.search.domain.model.Salary
import ru.practicum.android.diploma.search.domain.model.SimpleVacancy
import ru.practicum.android.diploma.sharing.data.ResourceProvider
import ru.practicum.android.diploma.util.Constants
import ru.practicum.android.diploma.util.Resource

class SearchRepositoryImpl(
    private val resourceProvider: ResourceProvider,
    private val client: NetworkClient,
) : SearchRepository {

    override fun searchVacancy(
        expression: String,
        options: HashMap<String, String>,
    ): Flow<Resource<List<SimpleVacancy>>> = flow {
        val response = client.doRequest(SearchRequest(expression), options)
        when (response.resultCode) {
            Constants.CONNECTION_ERROR -> emit(handleConnectionError())
            Constants.SUCCESS -> emit(handleSuccessResponse(response as SearchResponse))
            else -> emit(handleServerError())
        }
    }

    private fun handleConnectionError(): Resource.Error<List<SimpleVacancy>> {
        return Resource.Error(resourceProvider.getErrorInternetConnection())
    }

    private fun handleServerError(): Resource.Error<List<SimpleVacancy>> {
        return Resource.Error(resourceProvider.getErrorServer())
    }

    private fun handleSuccessResponse(response: SearchResponse): Resource.Success<List<SimpleVacancy>> {
        val vacancyDtoList = response.results
        val vacancyList = vacancyDtoList.map { vacancy ->
            SimpleVacancy(
                id = vacancy.id,
                name = vacancy.name,
                address = vacancy.address?.city,
                employer = Employer(
                    logoUrls =
                    vacancy.employer?.logoUrls?.original,
                    name = vacancy.employer?.name
                ),
                salary = Salary(
                    currency = vacancy.salary?.currency,
                    from = vacancy.salary?.from,
                    to = vacancy.salary?.to,
                ),
                found = response.found
            )
        }
        return if (vacancyList.isNotEmpty()) {
            (Resource.Success(vacancyList))
        } else {
            (Resource.Success(emptyList()))
        }
    }
}
