package ru.practicum.android.diploma.filter.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class FilterSearch(
    val countryId: String? = null,
    val regionId: String? = null,
    val industryId: String? = null,
    val expectedSalary: Long? = null,
    val isOnlyWithSalary: Boolean? = false
) : Parcelable
