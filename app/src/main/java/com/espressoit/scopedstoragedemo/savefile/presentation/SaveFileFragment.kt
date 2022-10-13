package com.espressoit.scopedstoragedemo.savefile.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.espressoit.scopedstoragedemo.databinding.FragmentSaveFileBinding
import com.espressoit.scopedstoragedemo.extensions.launchOnStartLifecycle
import com.espressoit.scopedstoragedemo.extensions.showToast
import com.espressoit.scopedstoragedemo.savefile.presentation.SaveFileAction.*
import com.espressoit.scopedstoragedemo.storage.pathchooser.FilePathChooser
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SaveFileFragment : Fragment() {

    private val filePathChooser: FilePathChooser by inject()
    private val viewModel: SaveFileViewModel by viewModel()

    private lateinit var binding: FragmentSaveFileBinding

    private val openAutomaticUriChooser = filePathChooser.registerOpeningDocumentTree(this) {
        if (it != null) {
            viewModel.chooseCustomDirectory(it)
        } else {
            showToast("User canceled choosing file path")
        }
    }

    private val openOneTimeUriChooser = filePathChooser.registerCreateDocument(this, "text/plain") {
        if (it != null) {
            viewModel.saveFile(it)
        } else {
            showToast("User canceled choosing file path")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSaveFileBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOnClicks()
        observeViewModelActions()
        observeFilePath()
    }

    private fun setupOnClicks() = with(binding) {
        singleSaveButton.setOnClickListener { viewModel.openOneTimeUriChooser() }
        autoSaveButton.setOnClickListener { viewModel.openAutomaticUriChooser() }
        resetPathButton.setOnClickListener { viewModel.resetSaveFilePath() }
    }

    private fun observeFilePath() = launchOnStartLifecycle {
        viewModel.filePath.collect { binding.filePathText.text = it }
    }

    private fun observeViewModelActions() = launchOnStartLifecycle {
        viewModel.sideEffect.collect(::handleAction)
    }

    private fun handleAction(action: SaveFileAction) = when (action) {
        is ShowToast -> showToast(action.message)
        is ShowLocationSnackBar -> showLocationSnackBar(action.message, action.intent)
        OpenAutomaticUriChooser -> openAutomaticUriChooser.launch()
        OpenOneTimeUriChooser -> openOneTimeUriChooser.launch(viewModel.generateRandomFileName())
    }

    private fun showLocationSnackBar(message: String, intent: Intent) = Snackbar
        .make(binding.root, message, Snackbar.LENGTH_LONG)
        .setAction("Locate") { startActivity(intent) }
        .show()
}
