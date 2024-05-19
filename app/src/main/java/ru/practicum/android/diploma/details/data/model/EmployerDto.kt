package ru.practicum.android.diploma.details.data.model

import com.google.gson.annotations.SerializedName

data class EmployerDto(
    @SerializedName("logoUrls") val logoUrls: LogoUrlsDto?,
    @SerializedName("name") val name: String?,
)
