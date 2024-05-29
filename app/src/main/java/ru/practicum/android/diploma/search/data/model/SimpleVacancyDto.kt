package ru.practicum.android.diploma.search.data.model

import com.google.gson.annotations.SerializedName

data class SimpleVacancyDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: AddressDto?,
    @SerializedName("employer") val employer: EmployerDto?,
    @SerializedName("salary") val salary: SalaryDto?,
)
