package ru.practicum.android.diploma.search.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.http.NetworkException
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
        if (!isConnected()) return Response().apply { resultCode = ERROR_1 }
        if (dto !is SearchRequest) return Response().apply { resultCode = ERROR_400 }

        return withContext(Dispatchers.IO) {
            try {
                val response = service.searchVacancy(dto.expression)
                response.apply { resultCode = ERROR_200 }
            } catch (exception: IOException) {
                Response().apply { resultCode = ERROR_500 }
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
