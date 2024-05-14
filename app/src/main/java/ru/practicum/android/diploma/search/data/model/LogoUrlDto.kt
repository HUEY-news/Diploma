package ru.practicum.android.diploma.search.data.model

import com.google.gson.annotations.SerializedName

data class LogoUrlDto(
    @SerializedName("90") val n90: String?,
    @SerializedName("240") val n240: String?,
    @SerializedName("original") val original: String?,
)
