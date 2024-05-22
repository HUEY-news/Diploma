package ru.practicum.android.diploma.search.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.search.data.dto.Response
import ru.practicum.android.diploma.search.data.dto.SearchRequest
import ru.practicum.android.diploma.util.CheckConnection
import ru.practicum.android.diploma.util.Constants
import java.io.IOException

class RetrofitNetworkClient(
    private val service: SearchApiService,
    private val checkConnection: CheckConnection,
) : NetworkClient {

    override suspend fun doRequest(dto: Any, options: HashMap<String, String>): Response {
        return when {
            !checkConnection.isInternetAvailable() -> {
                Response().apply { resultCode = Constants.CONNECTION_ERROR }
            }

            dto !is SearchRequest -> {
                Response().apply { resultCode = Constants.NOT_FOUND }
            }

            else -> {
                withContext(Dispatchers.IO) {
                    try {
                        val response = service.searchVacancy(dto.expression, options)
                        response.apply { resultCode = Constants.SUCCESS }
                    } catch (exception: IOException) {
                        Log.e("TEST", "$exception")
                        Response().apply { resultCode = Constants.SERVER_ERROR }
                    }
                }
            }
        }
    }

    override suspend fun doRequestDetails(dto: Any): Response {
        return when {
            !checkConnection.isInternetAvailable() -> {
                Response().apply { resultCode = Constants.CONNECTION_ERROR }
            }

            dto !is SearchRequest -> {
                Response().apply { resultCode = Constants.NOT_FOUND }
            }

            else -> {
                withContext(Dispatchers.IO) {
                    try {
                        val response = service.searchVacancyDetails(dto.expression)
                        response.apply { resultCode = Constants.SUCCESS }
                    } catch (exception: IOException) {
                        Log.e("TEST", "$exception")
                        Response().apply { resultCode = Constants.SERVER_ERROR }
                    }
                }
            }
        }
    }

    override suspend fun doRequestIndustries(): Response {
        return when {
            !checkConnection.isInternetAvailable() -> {
                Response().apply { resultCode = Constants.CONNECTION_ERROR }
            }

            else -> {
                withContext(Dispatchers.IO) {
                    try {
                        val response = service.searchIndustries()
                        response.apply { resultCode = Constants.SUCCESS }
                    } catch (exception: IOException) {
                        Log.e("TEST", "$exception")
                        Response().apply { resultCode = Constants.SERVER_ERROR }
                    }
                }
            }
        }
    }
}
