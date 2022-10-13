package com.espressoit.scopedstoragedemo.savefile.di

import com.espressoit.scopedstoragedemo.savefile.presentation.SaveFileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val saveFileModule = module {

    viewModel {
        SaveFileViewModel(
            checkAccessToUriDirectoryUseCase = get(),
            updateExportDirectoryUriUseCase = get(),
            resetExportDirectoryUriUseCase = get(),
            fileSaveRepository = get(),
            scopedStorageManager = get(),
            generateOutputFileUriUseCase = get(),
            generateTextFileUseCase = get()
        )
    }
}
