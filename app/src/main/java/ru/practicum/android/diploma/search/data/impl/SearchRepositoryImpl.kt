package ru.practicum.android.diploma.search.data.impl

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.search.data.dto.SearchRequest
import ru.practicum.android.diploma.search.data.dto.SearchResponse
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.model.Employer
import ru.practicum.android.diploma.search.domain.model.Salary
import ru.practicum.android.diploma.search.domain.model.SimpleVacancy
import ru.practicum.android.diploma.util.Constants
import ru.practicum.android.diploma.util.Resource

class SearchRepositoryImpl(
    private val context: Context,
    private val client: NetworkClient,
) : SearchRepository {

    private val errorEmptyText: String = "context.resources.getString(R.string.ПУСТО)"
    private val errorInternetText: String = "context.resources.getString(R.string.НЕТ ИНТЕРНЕТА)"
    private val errorServerText: String = "context.resources.getString(R.string.ОШИБКА СЕРВЕРА)"

    override fun searchVacancy(expression: String): Flow<Resource<List<SimpleVacancy>>> = flow {
        val response = client.doRequest(SearchRequest(expression))
        when (response.resultCode) {
            Constants.CONNECTION_ERROR -> emit(Resource.Error(errorInternetText))

            Constants.SUCCESS -> {
                val vacancyDtoList = (response as SearchResponse).results
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
                    )
                }
                if (vacancyList.isNotEmpty()) {
                    emit(Resource.Success(vacancyList))
                } else {
                    emit(Resource.Error(errorEmptyText))
                }
            }

            else -> emit(Resource.Error(errorServerText))
        }
    }
}
