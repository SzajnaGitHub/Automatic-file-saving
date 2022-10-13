package com.espressoit.scopedstoragedemo.storage

import android.os.Build
import android.webkit.MimeTypeMap
import androidx.annotation.ChecksSdkIntAtLeast

fun String.findMimeType(): String = MimeTypeMap.getSingleton()
    .getMimeTypeFromExtension(findExtension())
    .orEmpty()

private fun String.findExtension(): String =
    substringAfterLast('.', "")

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.Q)
fun isAtLeastAndroid10(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
