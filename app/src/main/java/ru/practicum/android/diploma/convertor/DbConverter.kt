package ru.practicum.android.diploma.convertor

import ru.practicum.android.diploma.favorite.data.db.FavoriteVacancyEntity
import ru.practicum.android.diploma.favorite.data.model.PhonesDto
import ru.practicum.android.diploma.favorite.domain.model.FavoriteVacancy
import ru.practicum.android.diploma.favorite.domain.model.Phones

class DbConverter {

    fun map(vacancy: FavoriteVacancy): FavoriteVacancyEntity = FavoriteVacancyEntity(
        id = vacancy.id.toInt(),
        name = vacancy.name,
        city = vacancy.city,
        employer = vacancy.employer,
        employerLogoUrls = vacancy.employerLogoUrls,
        currency = vacancy.currency,
        salaryFrom = vacancy.salaryFrom,
        salaryTo = vacancy.salaryTo,
        experience = vacancy.experience,
        employmentType = vacancy.employmentType,
        schedule = vacancy.schedule,
        description = vacancy.description,
        keySkills = vacancy.keySkills,
        phone = vacancy.phone?.map { map(it) },
        email = vacancy.email,
        contactPerson = vacancy.contactPerson,
        url = vacancy.url,
        addingTime = System.currentTimeMillis()
    )

    fun map(entity: FavoriteVacancyEntity): FavoriteVacancy = FavoriteVacancy(
        id = entity.id.toString(),
        name = entity.name,
        city = entity.city,
        employer = entity.employer,
        employerLogoUrls = entity.employerLogoUrls,
        currency = entity.currency,
        salaryFrom = entity.salaryFrom,
        salaryTo = entity.salaryTo,
        experience = entity.experience,
        employmentType = entity.employmentType,
        schedule = entity.schedule,
        description = entity.description,
        keySkills = entity.keySkills,
        phone = entity.phone?.map { map(it) },
        email = entity.email,
        contactPerson = entity.contactPerson,
        url = entity.url,
    )

    private fun map(phonesDto: PhonesDto): Phones {
        return Phones(
            country = phonesDto.country,
            city = phonesDto.city,
            number = phonesDto.number,
            comment = phonesDto.comment
        )
    }

    private fun map(phones: Phones): PhonesDto {
        return PhonesDto(
            country = phones.country,
            city = phones.city,
            number = phones.number,
            comment = phones.comment
        )
    }
}
