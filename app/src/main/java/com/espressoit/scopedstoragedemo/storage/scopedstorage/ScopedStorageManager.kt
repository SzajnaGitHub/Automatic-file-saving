package com.espressoit.scopedstoragedemo.storage.scopedstorage

import android.net.Uri
import java.io.File

interface ScopedStorageManager {

    fun requestPersistableUriPermission(contentUri: Uri)

    fun revertPersistableUriPermission(contentUri: Uri)

    fun isAccessToDirectoryUriGranted(contentUri: Uri): Boolean

    fun isAccessToFileUriGranted(contentUri: Uri): Boolean

    fun createFileInDirectoryTree(contentUri: Uri, fileName: String): Uri?

    fun findFileInDirectoryTree(contentUri: Uri, fileName: String): Uri?

    fun fetchDocumentFileUriFromUri(contentUri: Uri): Uri?

    fun copyFileToContentUri(contentUri: Uri, sourceFile: File)

    fun deleteFileFromContentUri(contentUri: Uri): Boolean

    fun getPathToDocumentsDirectory(): Uri
}
