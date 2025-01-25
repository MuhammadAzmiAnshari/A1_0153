package com.example.projectmanagement.ui.viewmodel.tim

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectmanagement.model.Tim
import com.example.projectmanagement.repository.TimRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeTimViewModel(
    private val timRepository: TimRepository
) : ViewModel() {

    private val _timList = MutableStateFlow<List<Tim>>(emptyList()) // Perbaiki penamaan variabel
    val timList: StateFlow<List<Tim>> get() = _timList // Sesuaikan dengan tipe data Tim

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    fun loadTim() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response = timRepository.getAllTim()
                if (response.isSuccessful) {
                    _timList.value = response.body() ?: emptyList() // Perbaiki penamaan variabel
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

    fun deleteTim(timId: Int) { // Ganti parameter proyekId dengan timId
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response = timRepository.deleteTim(timId) // Sesuaikan dengan TimRepository
                if (response.isSuccessful) {
                    loadTim() // Memuat ulang data setelah penghapusan
                } else {
                    _errorMessage.value = "Gagal menghapus tim: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}