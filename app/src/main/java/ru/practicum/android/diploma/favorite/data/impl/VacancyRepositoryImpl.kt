package ru.practicum.android.diploma.favorite.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.convertor.DbConverter
import ru.practicum.android.diploma.favorite.data.db.AppDatabase
import ru.practicum.android.diploma.favorite.data.db.FavoriteVacancyEntity
import ru.practicum.android.diploma.favorite.domain.api.VacancyRepository
import ru.practicum.android.diploma.favorite.domain.model.FavoriteVacancy

class VacancyRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val dbConverter: DbConverter
) : VacancyRepository {

    override fun getAllVacancies(): Flow<List<FavoriteVacancy>> = flow {
        val vacancyEntityList = appDatabase.vacancyDao().getAllItems()
        val vacancyList = convertFromVacancyEntity(vacancyEntityList.sortedByDescending { it.addingTime })
        emit(vacancyList)
    }

    private fun convertFromVacancyEntity(favoriteVacancyEntityList: List<FavoriteVacancyEntity>): List<FavoriteVacancy> {
        return favoriteVacancyEntityList.map { vacancyEntity -> dbConverter.map(vacancyEntity) }
    }
}
