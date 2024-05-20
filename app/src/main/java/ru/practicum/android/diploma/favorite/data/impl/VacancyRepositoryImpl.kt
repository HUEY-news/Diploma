package ru.practicum.android.diploma.favorite.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.convertor.DbConvertor
import ru.practicum.android.diploma.favorite.data.db.AppDatabase
import ru.practicum.android.diploma.favorite.data.db.VacancyEntity
import ru.practicum.android.diploma.favorite.domain.api.VacancyRepository
import ru.practicum.android.diploma.favorite.domain.model.Vacancy

class VacancyRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val dbConvertor: DbConvertor
) : VacancyRepository {

    override fun getAllVacancies(): Flow<List<Vacancy>> = flow {
        val vacancyEntityList = appDatabase.vacancyDao().getAllItems()
        val vacancyList = convertFromVacancyEntity(vacancyEntityList.sortedByDescending { it.addingTime })
        emit(vacancyList)
    }

    private fun convertFromVacancyEntity(vacancyEntityList: List<VacancyEntity>): List<Vacancy> {
        return vacancyEntityList.map { vacancyEntity -> dbConvertor.map(vacancyEntity) }
    }
}
