package ru.practicum.android.diploma.search.data.network

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.search.data.dto.Response
import ru.practicum.android.diploma.search.data.dto.SearchRequest
import ru.practicum.android.diploma.search.data.model.StateNetwork
import ru.practicum.android.diploma.util.CheckConnection
import java.io.IOException

class RetrofitNetworkClient(
    private val context: Context,
    private val service: SearchApiService,
    private val checkConnection: CheckConnection,
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        return if (!checkConnection.isInternetAvailable()) {
            Response().apply { resultCode = StateNetwork.CONNECTION_ERROR.code }
        } else if (dto !is SearchRequest) {
            Response().apply { resultCode = StateNetwork.NOT_FOUND.code }
        } else {
            withContext(Dispatchers.IO) {
                try {
                    val response = service.searchVacancy(dto.expression)
                    response.apply { resultCode = StateNetwork.SUCCESS.code }
                } catch (exception: IOException) {
                    Log.e("TEST", "$exception")
                    Response().apply { resultCode = StateNetwork.SERVER_ERROR.code }
                }
            }
        }
    }
}
