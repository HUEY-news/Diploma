package ru.practicum.android.diploma.db

class VacancyRepository(private val vacancyDao: VacancyDao) {
    fun getAllVacancies(): List<Vacancy> {
        return vacancyDao.getAllVacancies()
    }

}
