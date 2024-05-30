package ru.practicum.android.diploma.filter.presentation.industry.model

import ru.practicum.android.diploma.filter.domain.model.Industry

interface IndustryState {
    object Empty : IndustryState
    data class ContentIndustry(val industry: Industry) : IndustryState
}
