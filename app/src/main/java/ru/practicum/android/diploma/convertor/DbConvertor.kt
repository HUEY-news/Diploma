package ru.practicum.android.diploma.convertor

import ru.practicum.android.diploma.favorite.data.db.VacancyEntity
import ru.practicum.android.diploma.search.domain.model.Vacancy

class DbConvertor {

    fun map(vacancy: Vacancy): VacancyEntity {
        return VacancyEntity(
            vacancyId = vacancy.vacancyId,
            vacancyTitle = vacancy.vacancyTitle,
            vacancyDescription = vacancy.vacancyDescription
        )
    }

    fun map(vacancyEntity: VacancyEntity): Vacancy {
        return Vacancy(
            vacancyId = vacancyEntity.vacancyId,
            vacancyTitle = vacancyEntity.vacancyTitle,
            vacancyDescription = vacancyEntity.vacancyDescription
        )
    }
}
