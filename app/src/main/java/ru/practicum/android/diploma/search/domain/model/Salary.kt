package ru.practicum.android.diploma.search.domain.model

import java.math.BigDecimal

data class Salary(
    val currency: String?,
    val from: BigDecimal?,
    val to: BigDecimal?
)
