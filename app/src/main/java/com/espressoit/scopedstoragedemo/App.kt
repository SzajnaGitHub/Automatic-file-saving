package com.espressoit.scopedstoragedemo

import android.app.Application
import com.espressoit.scopedstoragedemo.savefile.di.saveFileModule
import com.espressoit.scopedstoragedemo.storage.storageModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(
                listOf(
                    saveFileModule,
                    storageModule
                )
            )
        }
    }
}
