package ru.practicum.android.diploma.search.data.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class SalaryDto(
    @SerializedName("currency") val currency: String?,
    @SerializedName("from") val from: BigDecimal?,
    @SerializedName("to") val to: BigDecimal?,
)
