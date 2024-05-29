package ru.practicum.android.diploma.search.data.model

import com.google.gson.annotations.SerializedName

data class EmployerDto(
    @SerializedName("logo_urls") val logoUrls: LogoUrlDto?,
    @SerializedName("name") val name: String?,
)
