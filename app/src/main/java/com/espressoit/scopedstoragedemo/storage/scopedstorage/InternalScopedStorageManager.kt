package com.espressoit.scopedstoragedemo.storage.scopedstorage

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import androidx.documentfile.provider.DocumentFile
import com.espressoit.scopedstoragedemo.extensions.orFalse
import com.espressoit.scopedstoragedemo.storage.findMimeType
import java.io.File
import java.io.FileOutputStream

internal class InternalScopedStorageManager(
    private val context: Context
) : ScopedStorageManager {

    override fun requestPersistableUriPermission(contentUri: Uri) {
        context.contentResolver?.takePersistableUriPermission(contentUri, FLAG_GRANT_URI_PERMISSION)
    }

    override fun revertPersistableUriPermission(contentUri: Uri) {
        context.contentResolver?.releasePersistableUriPermission(contentUri, FLAG_GRANT_URI_PERMISSION)
    }

    override fun isAccessToDirectoryUriGranted(contentUri: Uri): Boolean =
        DocumentFile.fromTreeUri(context, contentUri)
            ?.let { it.isDirectory && it.canRead() && it.canWrite() }
            .orFalse()

    override fun isAccessToFileUriGranted(contentUri: Uri): Boolean =
        DocumentFile.fromSingleUri(context, contentUri)
            ?.let { it.isFile && it.canRead() && it.canWrite() }
            .orFalse()

    override fun createFileInDirectoryTree(contentUri: Uri, fileName: String): Uri? =
        DocumentFile.fromTreeUri(context, contentUri)
            ?.createFile(fileName.findMimeType(), fileName)
            ?.uri

    override fun findFileInDirectoryTree(contentUri: Uri, fileName: String): Uri? =
        DocumentFile.fromTreeUri(context, contentUri)
            ?.findFile(fileName)
            ?.uri

    override fun fetchDocumentFileUriFromUri(contentUri: Uri): Uri? =
        DocumentFile.fromTreeUri(context, contentUri)?.uri

    override fun copyFileToContentUri(contentUri: Uri, sourceFile: File) {
        context.contentResolver.openFileDescriptor(contentUri, FILE_MODE_WRITE)?.use { parcelFileDescriptor ->
            FileOutputStream(parcelFileDescriptor.fileDescriptor).use { fileOutputStream ->
                sourceFile.inputStream().use { fileInputStream ->
                    fileInputStream.copyTo(fileOutputStream)
                }
            }
        }
    }

    override fun deleteFileFromContentUri(contentUri: Uri): Boolean =
        DocumentFile.fromSingleUri(context, contentUri)?.delete().orFalse()

    override fun getPathToDocumentsDirectory(): Uri =
        DocumentsContract.buildDocumentUri(EXTERNAL_STORAGE_AUTHORITY, PRIMARY_DOCUMENTS_DIRECTORY)

    private companion object {

        private const val FLAG_GRANT_URI_PERMISSION = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        private const val FILE_MODE_WRITE = "w"

        private const val EXTERNAL_STORAGE_AUTHORITY = "com.android.externalstorage.documents"
        private val PRIMARY_DOCUMENTS_DIRECTORY = "primary:" + Environment.DIRECTORY_DOCUMENTS
    }
}
