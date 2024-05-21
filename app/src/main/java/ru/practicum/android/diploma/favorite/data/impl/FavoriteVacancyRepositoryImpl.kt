package ru.practicum.android.diploma.favorite.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.convertor.DbConverter
import ru.practicum.android.diploma.favorite.data.db.AppDatabase
import ru.practicum.android.diploma.favorite.data.db.FavoriteVacancyEntity
import ru.practicum.android.diploma.favorite.domain.api.FavoriteVacancyRepository
import ru.practicum.android.diploma.favorite.domain.model.FavoriteVacancy
import ru.practicum.android.diploma.search.domain.model.SimpleVacancy

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

    override fun getAllFavoriteVacancies(): Flow<List<SimpleVacancy>> = flow {
        val entityList = appDatabase.favoriteVacancyDao().getAllItems()
        val itemList = convertFromVacancyEntity(entityList.sortedByDescending { it.addingTime })
        val simpleList = convertFromFavoriteVacancy(itemList)
        emit(simpleList)
    }

    override fun isVacancyFavorite(id: Int): Flow<Boolean> = flow {
        val isFavorite = appDatabase.favoriteVacancyDao().isItemExists(id)
        emit(isFavorite)
    }

    private fun convertFromVacancyEntity(entityList: List<FavoriteVacancyEntity>): List<FavoriteVacancy> {
        return entityList.map { vacancyEntity -> dbConverter.map(vacancyEntity) }
    }

    private fun convertFromFavoriteVacancy(vacancyList: List<FavoriteVacancy>): List<SimpleVacancy> {
        return vacancyList.map { favoriteVacancy -> dbConverter.mapFavoriteToSimple(favoriteVacancy) }
    }
}
