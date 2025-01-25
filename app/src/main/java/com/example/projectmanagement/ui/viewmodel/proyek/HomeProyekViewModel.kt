package com.example.projectmanagement.ui.viewmodel.proyek

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectmanagement.model.Proyek
import com.example.projectmanagement.repository.ProyekRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeProyekViewModel(
    private val proyekRepository: ProyekRepository
) : ViewModel() {

    private val _proyekList = MutableStateFlow<List<Proyek>>(emptyList())
    val proyekList: StateFlow<List<Proyek>> get() = _proyekList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    fun loadProyek() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response = proyekRepository.getAllProyek()
                if (response.isSuccessful) {
                    _proyekList.value = response.body() ?: emptyList()
                } else {
                    _errorMessage.value = "Gagal memuat data: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteProyek(proyekId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response = proyekRepository.deleteProyek(proyekId)
                if (response.isSuccessful) {
                    loadProyek() // Memuat ulang data setelah penghapusan
                } else {
                    _errorMessage.value = "Gagal menghapus proyek: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}