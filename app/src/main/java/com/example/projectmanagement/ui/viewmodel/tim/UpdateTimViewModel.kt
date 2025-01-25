package com.example.projectmanagement.ui.viewmodel.tim

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectmanagement.model.Tim
import com.example.projectmanagement.repository.TimRepository
import kotlinx.coroutines.launch

class UpdateTimViewModel(
    savedStateHandle: SavedStateHandle,
    private val timRepository: TimRepository
) : ViewModel() {
    val namaTim = mutableStateOf("")
    val deskripsiTim = mutableStateOf("")
    val isLoading = mutableStateOf(false)
    val isSuccess = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)
    val timId: Int = savedStateHandle.get<Int>("timId") ?: 0

    fun loadTimData() {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            try {
                val response = timRepository.getTimById(timId)
                if (response.isSuccessful) {
                    val tim = response.body()
                    if (tim != null) {
                        namaTim.value = tim.nama_tim
                        deskripsiTim.value = tim.deskripsi_tim
                    }
                } else {
                    errorMessage.value = "Gagal memuat data tim: ${response.message()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun updateTim() {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            isSuccess.value = false

            try {
                val updatedTim = Tim(
                    id_tim = timId,
                    nama_tim = namaTim.value,
                    deskripsi_tim = deskripsiTim.value
                )
                val response = timRepository.updateTim(timId, updatedTim)
                if (response.isSuccessful) {
                    isSuccess.value = true
                } else {
                    errorMessage.value = "Gagal memperbarui tim: ${response.message()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    // Fungsi untuk mereset form
    fun resetForm() {
        namaTim.value = ""
        deskripsiTim.value = ""
        isSuccess.value = false
        errorMessage.value = null
    }
}