package com.example.spot.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.spot.ThemeViewModel
import com.example.spot.data.remote.dtos.auth.AuthRepository
import com.example.spot.data.remote.dtos.UserPreferencesRepository
import com.example.spot.data.remote.dtos.auth.usecase.password.ForgotPasswordUseCase
import com.example.spot.data.remote.dtos.auth.usecase.password.NewPasswordUseCase
import com.example.spot.data.remote.dtos.auth.usecase.SignInUseCase
import com.example.spot.data.remote.dtos.auth.usecase.SignUpUseCase
import com.example.spot.data.remote.dtos.auth.usecase.confirm_code.ConfirmCodePasswordUseCase
import com.example.spot.data.remote.dtos.auth.usecase.confirm_code.ConfirmCodeSignUpUseCase
import com.example.spot.data.remote.dtos.create_profile.CreateProfileRepository
import com.example.spot.data.remote.dtos.home.establishment.EstablishmentRepository
import com.example.spot.data.remote.dtos.home.nextschedule.NextScheduleRepository
import com.example.spot.data.remote.dtos.schedules.AppointmentRepository
import com.example.spot.data.remote.network.AuthInterceptor
import com.example.spot.data.remote.network.SpotApiService
import com.example.spot.data.remote.dtos.auth.usecase.validators.EmailValidator
import com.example.spot.data.remote.dtos.auth.usecase.validators.PasswordValidator
import com.example.spot.data.remote.dtos.details.EstablishmentDetailsRepository
import com.example.spot.data.remote.dtos.favorite.FavoriteEstablishmentRepository
import com.example.spot.data.remote.dtos.schedule_service.ScheduleServiceRepository
import com.example.spot.ui.presentation.auth.viewmodel.AuthViewModel
import com.example.spot.ui.presentation.create_profile.viewmodel.CreateProfileViewModel
import com.example.spot.ui.presentation.details_establishment.screens.schedule_service.viewmodel.ScheduleServiceViewModel
import com.example.spot.ui.presentation.details_establishment.viewmodel.DetailsViewModel
import com.example.spot.ui.presentation.main_screen.favorite.viewmodel.FavoriteViewModel
import com.example.spot.ui.presentation.main_screen.home.viewmodel.HomeViewModel
import com.example.spot.ui.presentation.main_screen.profile.viewmodel.ProfileViewModel
import com.example.spot.ui.presentation.main_screen.schedules.viewmodel.ScheduleViewModel
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

private const val BASE_URL = "https://spot-server-ccgmcufudvfbbgec.eastus2-01.azurewebsites.net/"

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
            .addInterceptor(
                AuthInterceptor(
                    get()
                )
            )
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
    singleOf(::CreateProfileRepository)
    singleOf(::AppointmentRepository)
    singleOf(::EstablishmentDetailsRepository)
    singleOf(::FavoriteEstablishmentRepository)
    singleOf(::ScheduleServiceRepository)
}

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::AuthViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::CreateProfileViewModel)
    viewModelOf(::ScheduleViewModel)
    viewModelOf(::ThemeViewModel)
    viewModelOf(::DetailsViewModel)
    viewModelOf(::FavoriteViewModel)
    viewModelOf(::ScheduleServiceViewModel)
}

val storageModule = module {
    single {
        PreferenceDataStoreFactory.create {
            androidContext().preferencesDataStoreFile("user_preferences")
        }
    }
}

val useCaseModule = module {
    single { EmailValidator() }
    single { PasswordValidator() }

    singleOf(::ForgotPasswordUseCase)
    singleOf(::SignInUseCase)
    singleOf(::SignUpUseCase)
    singleOf(::NewPasswordUseCase)
    singleOf(::ConfirmCodePasswordUseCase)
    singleOf(::ConfirmCodeSignUpUseCase)
}