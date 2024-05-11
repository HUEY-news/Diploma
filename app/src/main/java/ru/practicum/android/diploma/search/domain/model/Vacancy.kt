package ru.practicum.android.diploma.search.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Vacancy(
    val vacancyId: Int,
    val vacancyTitle: String,
    val vacancyDescription: String,
    var isFavorite: Boolean = false
): Parcelable
