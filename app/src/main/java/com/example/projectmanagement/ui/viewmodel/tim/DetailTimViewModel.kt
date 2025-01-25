package com.example.projectmanagement.ui.viewmodel.tim

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectmanagement.model.Tim
import com.example.projectmanagement.repository.TimRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class DetailTimViewModel(
    private val timRepository: TimRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // ID tim yang diambil dari SavedStateHandle
    private val timId: Int = savedStateHandle.get<Int>("timId") ?: 0

    // StateFlow untuk menyimpan detail tim
    private val _tim = MutableStateFlow<Tim?>(null)
    val tim: StateFlow<Tim?> get() = _tim

    // StateFlow untuk menyimpan status loading
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    // StateFlow untuk menyimpan pesan error
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    // Fungsi untuk memuat detail tim dari repository
    fun loadTimDetail() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response: Response<Tim> = timRepository.getTimById(timId)
                if (response.isSuccessful) {
                    _tim.value = response.body()
                } else {
                    _errorMessage.value = "Gagal memuat detail tim: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Fungsi untuk memperbarui tim
    fun updateTim(tim: Tim) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response: Response<Tim> = timRepository.updateTim(timId, tim)
                if (response.isSuccessful) {
                    _tim.value = response.body()
                } else {
                    _errorMessage.value = "Gagal memperbarui tim: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}