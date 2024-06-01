package ru.practicum.android.diploma.filter.domain.model

data class Country(
    val areas: List<Area?>?,
    val id: String?,
    val name: String?,
    val parentId: String?,
)
