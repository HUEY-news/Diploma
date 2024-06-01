package ru.practicum.android.diploma.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.favorite.data.db.AppDatabase
import ru.practicum.android.diploma.filter.data.api.FiltrationStorage
import ru.practicum.android.diploma.filter.data.impl.FiltrationStorageImpl
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.search.data.network.SearchApiService
import ru.practicum.android.diploma.util.CheckConnection
import ru.practicum.android.diploma.util.Constants.PREFERENCES

val dataModule = module {

    single<NetworkClient> {
        RetrofitNetworkClient(
            service = get(),
            checkConnection = get(),
            resourceProvider = get()
        )
    }

    single<SearchApiService> {
        Retrofit.Builder()
            .baseUrl("https://api.hh.ru")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                    .build()
            )
            .build()
            .create(SearchApiService::class.java)
    }

    single { androidContext().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE) }
    single<FiltrationStorage> { FiltrationStorageImpl(prefs = get()) }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    factory { Gson() }

    factory { CheckConnection(get()) }
}
