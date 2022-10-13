package com.espressoit.scopedstoragedemo.storage.prefs

import android.content.SharedPreferences

internal class FileSavePreferences(private val prefs: SharedPreferences) {

    var filePath: String
        get() = prefs.getString(EXPORT_FILE_DIRECTORY_PATH_KEY, EXPORT_FILE_DIRECTORY_PATH_VALUE) ?: EXPORT_FILE_DIRECTORY_PATH_VALUE
        set(value) = prefs.write(EXPORT_FILE_DIRECTORY_PATH_KEY, value)

    companion object {
        private const val EXPORT_FILE_DIRECTORY_PATH_KEY = "export_file_directory_path"
        private const val EXPORT_FILE_DIRECTORY_PATH_VALUE = ""
    }
}
