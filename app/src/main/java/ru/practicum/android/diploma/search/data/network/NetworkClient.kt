package ru.practicum.android.diploma.search.data.network

import ru.practicum.android.diploma.search.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any, options: HashMap<String, String>): Response
}
