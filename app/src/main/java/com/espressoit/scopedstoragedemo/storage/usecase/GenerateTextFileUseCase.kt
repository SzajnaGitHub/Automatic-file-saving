package com.espressoit.scopedstoragedemo.storage.usecase

import android.content.Context
import java.io.File

class GenerateTextFileUseCase(
    private val context: Context
) {

    operator fun invoke(fileName: String): File {
        val root = File(context.cacheDir, "files")

        if (!root.exists()) {
            root.mkdirs()
        }

        val textFile = File(root, "${fileName}.txt")
        textFile.appendText("File with name: $fileName")

        textFile.createNewFile()
        return textFile
    }
}
