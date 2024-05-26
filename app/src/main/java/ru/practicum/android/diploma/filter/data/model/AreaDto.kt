package ru.practicum.android.diploma.filter.data.model

import com.google.gson.annotations.SerializedName

data class AreaDto(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?,
)
