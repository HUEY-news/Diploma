package ru.practicum.android.diploma.convertor

import ru.practicum.android.diploma.favorite.data.db.FavoriteVacancyEntity
import ru.practicum.android.diploma.favorite.domain.model.FavoriteVacancy
import ru.practicum.android.diploma.search.domain.model.SimpleVacancy

class DbConverter {

    fun map(vacancy: FavoriteVacancy): FavoriteVacancyEntity = FavoriteVacancyEntity(
        id = vacancy.id.toInt(),
        address = vacancy.address,
        alternateUrl = vacancy.alternateUrl,
        contacts = vacancy.contacts,
        description = vacancy.description,
        employer = vacancy.employer,
        experience = vacancy.experience,
        keySkills = vacancy.keySkills,
        name = vacancy.name,
        professionalRoles = vacancy.professionalRoles,
        salary = vacancy.salary,
        schedule = vacancy.schedule,
        addingTime = System.currentTimeMillis()
    )

    fun map(entity: FavoriteVacancyEntity): FavoriteVacancy = FavoriteVacancy(
        id = entity.id.toString(),
        address = entity.address,
        alternateUrl = entity.alternateUrl,
        contacts = entity.contacts,
        description = entity.description,
        employer = entity.employer,
        experience = entity.experience,
        keySkills = entity.keySkills,
        name = entity.name,
        professionalRoles = entity.professionalRoles,
        salary = entity.salary,
        schedule = entity.schedule
    )

    fun mapFavoriteToSimple(vacancy: FavoriteVacancy): SimpleVacancy = SimpleVacancy(
        id = vacancy.id,
        name = vacancy.name,
        address = vacancy.address,
        employer = vacancy.employer,
        salary = vacancy.salary,
        found = 0
    )
}
