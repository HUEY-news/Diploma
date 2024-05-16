package ru.practicum.android.diploma.search.data.dto

import ru.practicum.android.diploma.search.data.model.VacancyDto

data class SearchResponse(
    val results: List<VacancyDto>
) : Response()
