package ru.practicum.android.diploma.search.data.dto

import ru.practicum.android.diploma.search.data.model.SimpleVacancyDto

data class SearchResponse(
    val found: Long,
    val results: List<SimpleVacancyDto>,
    val page: Long,
    val pages: Long,
) : Response()
