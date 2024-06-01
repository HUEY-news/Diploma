package ru.practicum.android.diploma.filter.data.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.filter.data.model.IndustryDto

data class SearchIndustriesResponse(
    @SerializedName("industries") val results: List<IndustryDto>,
)
