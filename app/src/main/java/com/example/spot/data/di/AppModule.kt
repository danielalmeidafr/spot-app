package com.example.spot.data.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.spot.data.dtos.auth.AuthRepository
import com.example.spot.data.dtos.auth.UserPreferencesRepository
import com.example.spot.data.dtos.home.establishment.EstablishmentRepository
import com.example.spot.data.dtos.home.nextschedule.NextScheduleRepository
import com.example.spot.data.network.AuthInterceptor
import com.example.spot.data.network.SpotApiService
import com.example.spot.ui.presentation.auth.viewmodel.AuthViewModel
import com.example.spot.ui.presentation.main_screen.home.viewmodel.HomeViewModel
import com.example.spot.ui.presentation.main_screen.profile.viewmodel.ProfileViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://spot-server-production.up.railway.app/"

val networkModule = module {

    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(AuthInterceptor(
                get()
            ))
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .build()
    }

    single<SpotApiService> {
        get<Retrofit>().create(SpotApiService::class.java)
    }
}

val repositoryModule = module {
    singleOf(::EstablishmentRepository)
    singleOf(::NextScheduleRepository)
    singleOf(::UserPreferencesRepository)
    singleOf(::AuthRepository)
}

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::AuthViewModel)
    viewModelOf(::ProfileViewModel)
}

val storageModule = module {
    single {
        PreferenceDataStoreFactory.create {
            androidContext().preferencesDataStoreFile("user_preferences")
        }
    }
}