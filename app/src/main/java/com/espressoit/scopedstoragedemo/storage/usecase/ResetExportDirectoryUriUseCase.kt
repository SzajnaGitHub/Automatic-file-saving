package com.espressoit.scopedstoragedemo.storage.usecase

import com.espressoit.scopedstoragedemo.extensions.ifTrue
import com.espressoit.scopedstoragedemo.storage.save.FileSaveRepository
import com.espressoit.scopedstoragedemo.storage.scopedstorage.ScopedStorageManager

class ResetExportDirectoryUriUseCase(
    private val scopeStorageManager: ScopedStorageManager,
    private val fileSaveRepository: FileSaveRepository
) {

    operator fun invoke() {
        resetFileDirectoryUri()
        fileSaveRepository.resetFileUri()
    }

    private fun resetFileDirectoryUri() = runCatching {
        fileSaveRepository
            .isFileUriSelected()
            .ifTrue { scopeStorageManager.revertPersistableUriPermission(fileSaveRepository.getFileUriOrDefault()) }
    }
}
