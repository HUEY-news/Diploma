package ru.practicum.android.diploma.filter.data.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.filter.data.model.AreaDto

data class SearchAreasResponse(
    @SerializedName("areas") val areas: List<AreaDto?>?,
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?,
)
