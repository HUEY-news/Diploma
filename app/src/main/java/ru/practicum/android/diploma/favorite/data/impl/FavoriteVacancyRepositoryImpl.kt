package ru.practicum.android.diploma.favorite.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.convertor.DbConverter
import ru.practicum.android.diploma.favorite.data.db.AppDatabase
import ru.practicum.android.diploma.favorite.data.db.FavoriteVacancyEntity
import ru.practicum.android.diploma.favorite.domain.api.FavoriteVacancyRepository
import ru.practicum.android.diploma.favorite.domain.model.FavoriteVacancy

class FavoriteVacancyRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val dbConverter: DbConverter
) : FavoriteVacancyRepository {

    override suspend fun addVacancyToFavoriteList(vacancy: FavoriteVacancy) {
        val entity = dbConverter.map(vacancy)
        appDatabase.favoriteVacancyDao().addItem(entity)
    }

    override suspend fun removeVacancyFromFavoriteList(vacancy: FavoriteVacancy) {
        val entity = dbConverter.map(vacancy)
        appDatabase.favoriteVacancyDao().removeItem(entity)
    }

    override fun getVacancyFromFavoriteList(id: Int): Flow<FavoriteVacancy> = flow {
        val entity = appDatabase.favoriteVacancyDao().getItem(id)
        val item = dbConverter.map(entity)
        emit(item)
    }

    override fun getAllFavoriteVacancies(): Flow<List<FavoriteVacancy>> = flow {
        val entityList = appDatabase.favoriteVacancyDao().getAllItems()
        val itemList = convertFromVacancyEntity(entityList.sortedByDescending { it.addingTime })
        val idList = appDatabase.favoriteVacancyDao().getAllItemIds()
        for (item in itemList) item.isFavorite = idList.contains(item.id.toInt())
        emit(itemList)
    }

    private fun convertFromVacancyEntity(favoriteVacancyEntityList: List<FavoriteVacancyEntity>): List<FavoriteVacancy> {
        return favoriteVacancyEntityList.map { vacancyEntity -> dbConverter.map(vacancyEntity) }
    }
}
