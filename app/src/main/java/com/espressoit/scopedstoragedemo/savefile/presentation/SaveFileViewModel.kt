package com.espressoit.scopedstoragedemo.savefile.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.espressoit.scopedstoragedemo.extensions.isContentUriNavigable
import com.espressoit.scopedstoragedemo.extensions.resolveFilePath
import com.espressoit.scopedstoragedemo.extensions.toFileIntent
import com.espressoit.scopedstoragedemo.savefile.presentation.SaveFileAction.*
import com.espressoit.scopedstoragedemo.storage.isAtLeastAndroid10
import com.espressoit.scopedstoragedemo.storage.save.FileSaveRepository
import com.espressoit.scopedstoragedemo.storage.scopedstorage.ScopedStorageManager
import com.espressoit.scopedstoragedemo.storage.usecase.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

class SaveFileViewModel(
    private val fileSaveRepository: FileSaveRepository,
    private val scopedStorageManager: ScopedStorageManager,
    private val generateTextFileUseCase: GenerateTextFileUseCase,
    private val checkAccessToUriDirectoryUseCase: CheckAccessToUriDirectoryUseCase,
    private val generateOutputFileUriUseCase: GenerateOutputFileUriUseCase,
    private val updateExportDirectoryUriUseCase: UpdateExportDirectoryUriUseCase,
    private val resetExportDirectoryUriUseCase: ResetExportDirectoryUriUseCase,
) : ViewModel() {

    private val _filePath: MutableStateFlow<String> = MutableStateFlow("")
    val filePath: StateFlow<String> = _filePath.asStateFlow()

    private val _sideEffect: Channel<SaveFileAction> = Channel()
    val sideEffect: Flow<SaveFileAction> = _sideEffect.receiveAsFlow()

    private var fileName: String? = null

    init {
        fetchDefaultFilePath()
    }

    fun openOneTimeUriChooser() = viewModelScope.launch {
        _sideEffect.send(OpenOneTimeUriChooser)
    }

    fun openAutomaticUriChooser() = viewModelScope.launch {
        if (fileSaveRepository.isFileUriSelected()) {
            generateRandomFileName()
            saveFile()
        } else {
            _sideEffect.send(OpenAutomaticUriChooser)
        }
    }

    fun resetSaveFilePath() {
        resetExportDirectoryUriUseCase()
        fetchDefaultFilePath()
    }

    private fun fetchDefaultFilePath() {
        fileSaveRepository.getFileUriPathOrNull()
            .orEmpty()
            .let { _filePath.value = it }
    }

    fun generateRandomFileName() = UUID.randomUUID()
        .toString()
        .substring(0..5)
        .also { fileName = it }

    fun chooseCustomDirectory(contentUri: Uri) {
        when (checkAccessToUriDirectoryUseCase(contentUri)) {
            true -> handleCorrectDirectorySelected(contentUri)
            false -> handleForbiddenDirectorySelected()
        }
    }

    private fun handleCorrectDirectorySelected(contentUri: Uri) {
        updateExportDirectoryUriUseCase(contentUri)
        contentUri.path?.let { _filePath.value = it }
    }

    private fun handleForbiddenDirectorySelected() = viewModelScope.launch {
        resetExportDirectoryUriUseCase()
        _sideEffect.send(ShowToast("Forbidden directory selected"))
    }

    fun saveFile(uri: Uri? = null) {
        val name = fileName ?: generateRandomFileName()
        val file = generateTextFileUseCase(name)
        val contentUri = uri ?: generateOutputFileUriUseCase(name + FILE_EXTENSION)

        scopedStorageManager.copyFileToContentUri(contentUri, file)
        showLocation(contentUri = contentUri, fileName = name)
    }

    private fun showLocation(contentUri: Uri, fileName: String) = viewModelScope.launch {
        if (contentUri.isContentUriNavigable() && isAtLeastAndroid10()) {
            _sideEffect.send(
                ShowLocationSnackBar(
                    message = "File saved in ${contentUri.resolveFilePath(fileName)}",
                    intent = contentUri.toFileIntent()
                )
            )
        } else {
            _sideEffect.send(ShowToast("File saved in ${contentUri.resolveFilePath(fileName)}"))
        }
    }

    private companion object {
        const val FILE_EXTENSION = ".txt"
    }
}
