package ru.practicum.android.diploma.favorite.domain.model

data class Vacancy(
    val vacancyId: Int,
    val vacancyTitle: String,
    val vacancyDescription: String,
    val addingTime: Long = 0
)
