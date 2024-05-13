package ru.practicum.android.diploma.search.data.network

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.search.data.dto.Response
import ru.practicum.android.diploma.search.data.dto.SearchRequest
import ru.practicum.android.diploma.util.CheckConnection
import java.io.IOException

class RetrofitNetworkClient(
    private val context: Context,
    private val service: SearchApiService,
    private val checkConnection: CheckConnection,
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        return if (!checkConnection.isInternetAvailable()) {
            Response().apply { resultCode = ERROR_1 }
        } else if (dto !is SearchRequest) {
            Response().apply { resultCode = ERROR_400 }
        } else {
            withContext(Dispatchers.IO) {
                try {
                    val response = service.searchVacancy(dto.expression)
                    response.apply { resultCode = ERROR_200 }
                } catch (exception: IOException) {
                    Log.e("TEST", "$exception")
                    Response().apply { resultCode = ERROR_500 }
                }
            }
        }
    }

    companion object {
        const val ERROR_1 = -1
        const val ERROR_200 = 200
        const val ERROR_400 = 400
        const val ERROR_500 = 500
    }
}
