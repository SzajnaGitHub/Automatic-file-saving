package com.espressoit.scopedstoragedemo.storage

import android.content.Context.MODE_PRIVATE
import com.espressoit.scopedstoragedemo.storage.pathchooser.FilePathChooser
import com.espressoit.scopedstoragedemo.storage.prefs.FileSavePreferences
import com.espressoit.scopedstoragedemo.storage.save.FileSaveRepository
import com.espressoit.scopedstoragedemo.storage.save.InternalFileSaveRepository
import com.espressoit.scopedstoragedemo.storage.scopedstorage.InternalScopedStorageManager
import com.espressoit.scopedstoragedemo.storage.scopedstorage.ScopedStorageManager
import com.espressoit.scopedstoragedemo.storage.usecase.*
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val storageModule = module {

    single { androidApplication().getSharedPreferences("save_file_prefs", MODE_PRIVATE) }

    single { FileSavePreferences(get()) }

    single<ScopedStorageManager> { InternalScopedStorageManager(androidApplication()) }
    single<FileSaveRepository> { InternalFileSaveRepository(get(), get()) }

    factory { FilePathChooser(scopeStorageManager = get(), fileSaveRepository = get()) }

    factory { GenerateTextFileUseCase(get()) }
    factory { CheckAccessToUriDirectoryUseCase(get()) }
    factory { GenerateOutputFileUriUseCase(get(), get()) }
    factory { ResetExportDirectoryUriUseCase(get(), get()) }
    factory { UpdateExportDirectoryUriUseCase(get(), get()) }
}
