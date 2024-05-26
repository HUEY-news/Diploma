package ru.practicum.android.diploma.filter.domain.model

data class FiltersSave(
    val workPlace: String,
    val industry: String,
    val salary: String?,
    val checkbox: Boolean
)
