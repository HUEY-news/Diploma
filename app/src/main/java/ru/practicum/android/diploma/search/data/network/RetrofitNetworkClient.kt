package ru.practicum.android.diploma.search.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.search.data.dto.Response
import ru.practicum.android.diploma.search.data.dto.SearchRequest
import java.io.IOException

class RetrofitNetworkClient(
    private val context: Context,
    private val service: SearchApiService
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        return if (!isConnected()) {
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

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null &&
            (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
    }

    companion object {
        const val ERROR_1 = -1
        const val ERROR_200 = 200
        const val ERROR_400 = 400
        const val ERROR_500 = 500
    }
}
