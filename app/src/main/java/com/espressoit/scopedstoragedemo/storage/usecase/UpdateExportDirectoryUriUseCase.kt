package com.espressoit.scopedstoragedemo.storage.usecase

import android.net.Uri
import com.espressoit.scopedstoragedemo.storage.save.FileSaveRepository
import com.espressoit.scopedstoragedemo.storage.scopedstorage.ScopedStorageManager

class UpdateExportDirectoryUriUseCase(
    private val scopeStorageManager: ScopedStorageManager,
    private val fileSaveRepository: FileSaveRepository,
) {

    operator fun invoke(contentUri: Uri) {
        scopeStorageManager.requestPersistableUriPermission(contentUri)
        fileSaveRepository.updateFileUri(contentUri)
    }
}
