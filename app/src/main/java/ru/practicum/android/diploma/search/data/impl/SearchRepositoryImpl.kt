package ru.practicum.android.diploma.search.data.impl

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.search.data.dto.SearchRequest
import ru.practicum.android.diploma.search.data.dto.SearchResponse
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.model.Vacancy
import ru.practicum.android.diploma.util.Resource

class SearchRepositoryImpl(
    private val context: Context,
    private val client: NetworkClient
) : SearchRepository {

    private val errorEmptyText: String = "context.resources.getString(R.string.имя строки)"
    private val errorInternetText: String = "context.resources.getString(R.string.имя строки)"
    private val errorServerText: String = "context.resources.getString(R.string.имя строки)"

    override fun searchVacancy(expression: String): Flow<Resource<List<Vacancy>>> = flow {
        val response = client.doRequest(SearchRequest(expression))
        when (response.resultCode) {

            -1 -> emit(Resource.Error(errorInternetText))

            200 -> {
                val vacancyDtoList = (response as SearchResponse).results
                val vacancyList = vacancyDtoList.map {
                    Vacancy(
                        vacancyId = it.vacancyId,
                        vacancyTitle = it.vacancyTitle,
                        vacancyDescription = it.vacancyDescription
                    )
                }

                if (vacancyList.isNotEmpty()) emit(Resource.Success(vacancyList))
                else emit(Resource.Error(errorEmptyText))
            }
            else -> emit(Resource.Error(errorServerText))
        }
    }
}
