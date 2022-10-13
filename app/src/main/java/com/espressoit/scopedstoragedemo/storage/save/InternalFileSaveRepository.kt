/******************************************************************************
 * This program is protected under international and U.S. copyright laws as
 * an unpublished work. This program is confidential and proprietary to the
 * copyright owners. Reproduction or disclosure, in whole or in part, or the
 * production of derivative works therefrom without the express permission of
 * the copyright owners is prohibited.
 *
 *                 Copyright (C) 2018 by Dolby Laboratories.
 *                            All rights reserved.
 ******************************************************************************/

package com.espressoit.scopedstoragedemo.storage.save

import android.net.Uri
import com.espressoit.scopedstoragedemo.extensions.ifNotEmpty
import com.espressoit.scopedstoragedemo.storage.prefs.FileSavePreferences
import com.espressoit.scopedstoragedemo.storage.scopedstorage.ScopedStorageManager

internal class InternalFileSaveRepository(
    private val scopeStorageManager: ScopedStorageManager,
    private val prefs: FileSavePreferences
) : FileSaveRepository {

    override fun isFileUriSelected(): Boolean = prefs.filePath.isNotEmpty()

    override fun getFileUriOrDefault(): Uri = tryToParseStringToUri(prefs.filePath) ?: scopeStorageManager.getPathToDocumentsDirectory()

    override fun getFileUriPathOrNull(): String? = tryToParseStringToUri(prefs.filePath)?.path

    private fun tryToParseStringToUri(input: String) = input.ifNotEmpty { Uri.parse(input) }

    override fun updateFileUri(uri: Uri) {
        prefs.filePath = uri.toString()
    }

    override fun resetFileUri() {
        prefs.filePath = ""
    }
}
