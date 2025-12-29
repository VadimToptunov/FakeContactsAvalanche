package com.toptunov.fakecontactsavalanche

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for MainActivity - manages UI state and business logic
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ContactsRepository(application)
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    private var currentJob: Job? = null

    fun startGenerating(count: Int) {
        val context = getApplication<Application>()
        if (count <= 0) {
            _uiState.value = UiState.Error(context.getString(R.string.error_must_be_positive))
            return
        }
        
        if (count > MAX_CONTACTS) {
            _uiState.value = UiState.Error(
                context.getString(R.string.error_max_exceeded, MAX_CONTACTS)
            )
            return
        }

        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            _uiState.value = UiState.Loading(current = 0, total = count)
            
            try {
                val successCount = repository.createMultipleContacts(count) { current, total ->
                    _uiState.value = UiState.Loading(current = current, total = total)
                }
                
                if (successCount == count) {
                    _uiState.value = UiState.Success(
                        message = context.resources.getQuantityString(
                            R.plurals.contacts_created,
                            successCount,
                            successCount
                        ),
                        count = successCount
                    )
                } else {
                    _uiState.value = UiState.Warning(
                        message = context.getString(
                            R.string.warning_partial,
                            successCount,
                            count
                        ),
                        successCount = successCount,
                        totalCount = count
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = UiState.Error("${context.getString(R.string.error_creating_contacts)}: ${e.message}")
            }
        }
    }

    fun cancelGeneration() {
        currentJob?.cancel()
        _uiState.value = UiState.Idle
    }

    fun resetState() {
        _uiState.value = UiState.Idle
    }

    companion object {
        private const val MAX_CONTACTS = 10000
    }
}
sealed class UiState {
    object Idle : UiState()
    
    data class Loading(
        val current: Int,
        val total: Int
    ) : UiState() {
        val progress: Int
            get() = if (total > 0) (current * 100L / total).toInt() else 0
    }
    
    data class Success(
        val message: String,
        val count: Int
    ) : UiState()
    
    data class Warning(
        val message: String,
        val successCount: Int,
        val totalCount: Int
    ) : UiState()
    
    data class Error(
        val message: String
    ) : UiState()
}

