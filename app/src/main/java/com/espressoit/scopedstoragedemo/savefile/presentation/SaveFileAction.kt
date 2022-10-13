package com.espressoit.scopedstoragedemo.savefile.presentation

import android.content.Intent

sealed interface SaveFileAction {
    data class ShowToast(val message: String) : SaveFileAction
    data class ShowLocationSnackBar(val message: String, val intent: Intent) : SaveFileAction
    object OpenOneTimeUriChooser : SaveFileAction
    object OpenAutomaticUriChooser : SaveFileAction
}
