package ru.practicum.android.diploma.details.data.model

import com.google.gson.annotations.SerializedName

data class EmployerDto(
    @SerializedName("logo_urls") val logoUrls: LogoUrlsDto?,
    @SerializedName("name") val name: String?,
)
