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

interface FileSaveRepository {

    fun isFileUriSelected(): Boolean
    fun getFileUriOrDefault(): Uri
    fun getFileUriPathOrNull(): String?

    fun updateFileUri(uri: Uri)
    fun resetFileUri()
}
