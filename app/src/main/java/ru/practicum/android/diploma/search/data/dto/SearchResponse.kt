package ru.practicum.android.diploma.search.data.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.search.data.model.SimpleVacancyDto

data class SearchResponse(
    @SerializedName("found") val found: Long,
    @SerializedName("items") val results: List<SimpleVacancyDto>,
    @SerializedName("page") val page: Long,
    @SerializedName("pages") val pages: Long,
) : Response()
