package com.example.projectmanagement.ui.viewmodel.tugas

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectmanagement.model.Tugas
import com.example.projectmanagement.repository.TugasRepository
import kotlinx.coroutines.launch

class DetailTugasViewModel(
    private val tugasRepository: TugasRepository
) : ViewModel() {

    // State untuk detail tugas
    val tugas = mutableStateOf<Tugas?>(null)

    // State untuk loading
    val isLoading = mutableStateOf(false)

    // State untuk pesan error
    val errorMessage = mutableStateOf<String?>(null)

    // Fungsi untuk memuat detail tugas
    fun loadTugas(tugasId: Int) {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            try {
                val response = tugasRepository.getTugasById(tugasId)
                if (response.isSuccessful) {
                    tugas.value = response.body()
                } else {
                    errorMessage.value = "Gagal memuat detail tugas: ${response.message()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    // Fungsi untuk menghapus tugas
    fun deleteTugas(tugasId: Int) {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            try {
                val response = tugasRepository.deleteTugas(tugasId)
                if (response.isSuccessful) {
                    // Tugas berhasil dihapus
                } else {
                    errorMessage.value = "Gagal menghapus tugas: ${response.message()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }
}