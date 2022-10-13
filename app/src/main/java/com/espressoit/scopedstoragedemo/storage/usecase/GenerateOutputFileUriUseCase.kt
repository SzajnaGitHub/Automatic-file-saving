package com.espressoit.scopedstoragedemo.storage.usecase

import android.net.Uri
import com.espressoit.scopedstoragedemo.storage.save.FileSaveRepository
import com.espressoit.scopedstoragedemo.storage.scopedstorage.ScopedStorageManager

class GenerateOutputFileUriUseCase(
    private val scopedStorageManager: ScopedStorageManager,
    private val fileSaveRepository: FileSaveRepository
) {

    operator fun invoke(fileName: String): Uri {
        val directoryUri = fileSaveRepository.getFileUriOrDefault()

        return if (scopedStorageManager.isAccessToDirectoryUriGranted(directoryUri)) {
            scopedStorageManager.createFileInDirectoryTree(directoryUri, fileName) ?: throw IllegalUriAccessException
        } else {
            throw IllegalUriAccessException
        }
    }

    object IllegalUriAccessException : Exception()
}
