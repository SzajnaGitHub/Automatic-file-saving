package com.espressoit.scopedstoragedemo.extensions

import android.content.Intent
import android.net.Uri
import android.provider.DocumentsContract

private const val ANDROID_DOWNLOAD_AUTHORITY = "com.android.providers.downloads.documents"
private const val GOOGLE_DRIVE_AUTHORITY = "com.google.android.apps.docs.storage"
private const val ANDROID_DOWNLOAD_DIRECTORY = "Download"

fun Uri.resolveFilePath(fileName: String): String = when (authority) {
    ANDROID_DOWNLOAD_AUTHORITY -> "$ANDROID_DOWNLOAD_DIRECTORY/$fileName"
    else -> lastPathSegment?.removePrefix("primary:").orEmpty()
}

fun Uri.toFileIntent(): Intent = Intent(Intent.ACTION_VIEW).apply {
    setDataAndTypeAndNormalize(this@toFileIntent, DocumentsContract.Document.MIME_TYPE_DIR)
    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
}

fun Uri.isContentUriNavigable(): Boolean = when (authority) {
    GOOGLE_DRIVE_AUTHORITY, null -> false
    else -> true
}
