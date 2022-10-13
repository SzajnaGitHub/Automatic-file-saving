package com.espressoit.scopedstoragedemo.storage.pathchooser

import android.net.Uri
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.CreateDocument
import androidx.activity.result.contract.ActivityResultContracts.OpenDocumentTree
import androidx.fragment.app.Fragment
import com.espressoit.scopedstoragedemo.extensions.ifTrue
import com.espressoit.scopedstoragedemo.extensions.orDefault
import com.espressoit.scopedstoragedemo.storage.save.FileSaveRepository
import com.espressoit.scopedstoragedemo.storage.scopedstorage.ScopedStorageManager

class FilePathChooser(
    private val scopeStorageManager: ScopedStorageManager,
    private val fileSaveRepository: FileSaveRepository
) {

    fun registerOpeningDocumentTree(
        fragment: Fragment,
        callback: ActivityResultCallback<Uri?>
    ): OpenDocumentTreeLauncher = OpenDocumentTreeLauncher(fragment.registerForActivityResult(OpenDocumentTree(), callback))

    inner class OpenDocumentTreeLauncher(
        private val resultLauncher: ActivityResultLauncher<Uri?>
    ) {

        fun launch() = resultLauncher.launch(getInitialUri())

        private fun getInitialUri(): Uri = fileSaveRepository.isFileUriSelected()
            .ifTrue { scopeStorageManager.fetchDocumentFileUriFromUri(fileSaveRepository.getFileUriOrDefault()) }
            .orDefault { fileSaveRepository.getFileUriOrDefault() }
    }

    fun registerCreateDocument(
        fragment: Fragment,
        mimeType: String,
        callback: ActivityResultCallback<Uri?>
    ): CreateDocumentLauncher = CreateDocumentLauncher(fragment.registerForActivityResult(CreateDocument(mimeType), callback))

    inner class CreateDocumentLauncher(
        private val resultLauncher: ActivityResultLauncher<String?>
    ) {

        fun launch(fileName: String) = resultLauncher.launch(fileName)
    }
}
