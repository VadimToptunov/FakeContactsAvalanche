package com.toptunov.fakecontactsavalanche

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.toptunov.fakecontactsavalanche.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            startGeneration()
        } else {
            showPermissionDeniedDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.contactsQuantity.addTextChangedListener {
            binding.generateButton.isEnabled = !it.isNullOrBlank()
        }

        binding.generateButton.setOnClickListener {
            if (checkPermissions()) {
                startGeneration()
            } else {
                requestPermissions()
            }
        }

        binding.cancelButton.setOnClickListener {
            viewModel.cancelGeneration()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                updateUI(state)
            }
        }
    }

    private fun updateUI(state: UiState) {
        when (state) {
            is UiState.Idle -> {
                binding.progressCard.visibility = View.GONE
                binding.cancelButton.visibility = View.GONE
                binding.statusText.visibility = View.GONE
                binding.generateButton.isEnabled = !binding.contactsQuantity.text.isNullOrBlank()
                binding.inputLayout.isEnabled = true
            }

            is UiState.Loading -> {
                binding.progressCard.visibility = View.VISIBLE
                binding.cancelButton.visibility = View.VISIBLE
                binding.generateButton.isEnabled = false
                binding.inputLayout.isEnabled = false
                binding.statusText.visibility = View.GONE

                binding.progressBar.progress = state.progress
                binding.progressBar.max = 100
                binding.progressText.text = getString(
                    R.string.progress_creating,
                    state.current,
                    state.total
                )
                binding.percentageText.text = "${state.progress}%"
            }

            is UiState.Success -> {
                binding.progressCard.visibility = View.GONE
                binding.cancelButton.visibility = View.GONE
                binding.statusText.visibility = View.VISIBLE
                binding.statusText.text = state.message
                binding.generateButton.isEnabled = true
                binding.inputLayout.isEnabled = true
                binding.contactsQuantity.text?.clear()

                Snackbar.make(binding.root, state.message, Snackbar.LENGTH_LONG)
                    .setAction("OK") { viewModel.resetState() }
                    .show()
            }

            is UiState.Warning -> {
                binding.progressCard.visibility = View.GONE
                binding.cancelButton.visibility = View.GONE
                binding.statusText.visibility = View.VISIBLE
                binding.statusText.text = state.message
                binding.generateButton.isEnabled = true
                binding.inputLayout.isEnabled = true

                Snackbar.make(binding.root, state.message, Snackbar.LENGTH_LONG)
                    .setAction("OK") { viewModel.resetState() }
                    .show()
            }

            is UiState.Error -> {
                binding.progressCard.visibility = View.GONE
                binding.cancelButton.visibility = View.GONE
                binding.statusText.visibility = View.VISIBLE
                binding.statusText.text = state.message
                binding.generateButton.isEnabled = true
                binding.inputLayout.isEnabled = true

                Snackbar.make(binding.root, state.message, Snackbar.LENGTH_LONG)
                    .setAction("OK") { viewModel.resetState() }
                    .show()
            }
        }
    }

    private fun startGeneration() {
        val countText = binding.contactsQuantity.text.toString()
        
        if (countText.isBlank()) {
            showError(getString(R.string.error_empty_input))
            return
        }

        val count = try {
            countText.toInt()
        } catch (e: NumberFormatException) {
            showError(getString(R.string.error_invalid_number))
            return
        }

        viewModel.startGenerating(count)
    }

    private fun checkPermissions(): Boolean {
        val readGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED

        val writeGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED

        return readGranted && writeGranted
    }

    private fun requestPermissions() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS) ||
            shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)
        ) {
            showPermissionRationaleDialog()
        } else {
            launchPermissionRequest()
        }
    }

    private fun launchPermissionRequest() {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS
            )
        )
    }

    private fun showPermissionRationaleDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.permission_required)
            .setMessage(R.string.permission_rationale)
            .setPositiveButton(R.string.dialog_ok) { _, _ ->
                launchPermissionRequest()
            }
            .setNegativeButton(R.string.dialog_cancel, null)
            .show()
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.permission_required)
            .setMessage(R.string.permission_denied)
            .setPositiveButton(R.string.dialog_try_again) { _, _ ->
                requestPermissions()
            }
            .setNegativeButton(R.string.dialog_cancel, null)
            .show()
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}