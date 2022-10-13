package com.espressoit.scopedstoragedemo.storage.usecase

import android.net.Uri
import com.espressoit.scopedstoragedemo.extensions.orFalse
import com.espressoit.scopedstoragedemo.storage.scopedstorage.ScopedStorageManager

class CheckAccessToUriDirectoryUseCase(
    private val scopedStorageManager: ScopedStorageManager
) {

    operator fun invoke(input: Uri): Boolean =
        tryFileModificationInDirectoryUri(input)
            .getOrDefault(false)
            .also { cleanupTempFileFromDirectoryUri(input) }

    private fun tryFileModificationInDirectoryUri(contentUri: Uri) = runCatching {
        scopedStorageManager.createFileInDirectoryTree(contentUri, TMP_FILE)?.let { fileUri ->
            scopedStorageManager.isAccessToFileUriGranted(fileUri)
        }.orFalse()
    }

    private fun cleanupTempFileFromDirectoryUri(contentUri: Uri) = runCatching {
        scopedStorageManager.findFileInDirectoryTree(contentUri, TMP_FILE)?.let { fileUri ->
            scopedStorageManager.deleteFileFromContentUri(fileUri)
        }
    }

    companion object {
        private const val TMP_FILE = ".temp.export.log"
    }
}
