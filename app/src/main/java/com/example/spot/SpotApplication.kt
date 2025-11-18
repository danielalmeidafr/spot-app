package com.example.spot

import android.app.Application
import com.example.spot.di.networkModule
import com.example.spot.di.repositoryModule
import com.example.spot.di.storageModule
import com.example.spot.di.useCaseModule
import com.example.spot.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SpotApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@SpotApplication)
            modules(
                listOf(
                    storageModule,
                    repositoryModule,
                    networkModule,
                    viewModelModule,
                    useCaseModule
                )
            )
        }
    }
}
