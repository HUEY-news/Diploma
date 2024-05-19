package ru.practicum.android.diploma.details.data.model

import com.google.gson.annotations.SerializedName

data class ContactsDto(
    @SerializedName("email") val email: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("phones") val phones: List<PhoneDto?>,
)
