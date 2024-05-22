package ru.practicum.android.diploma.filter.data.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.filter.data.model.IndustryDto
import ru.practicum.android.diploma.search.data.dto.Response

data class SearchIndustriesResponse(
    @SerializedName("industries") val results: List<IndustryDto>,
) : Response()
