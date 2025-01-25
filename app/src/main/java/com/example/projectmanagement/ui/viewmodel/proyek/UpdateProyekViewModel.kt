package com.example.projectmanagement.ui.viewmodel.proyek

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectmanagement.model.Proyek
import com.example.projectmanagement.repository.ProyekRepository
import kotlinx.coroutines.launch

class UpdateProyekViewModel(
    savedStateHandle: SavedStateHandle,
    private val proyekRepository: ProyekRepository
) : ViewModel() {
    val namaProyek = mutableStateOf("")
    val deskripsiProyek = mutableStateOf("")
    val tanggalMulai = mutableStateOf("")
    val tanggalBerakhir = mutableStateOf("")
    val statusProyek = mutableStateOf("Aktif")
    val isLoading = mutableStateOf(false)
    val isSuccess = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)
    val proyekId: Int = savedStateHandle.get<Int>("proyekId") ?: 0

    fun loadProyekData() {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            try {
                val response = proyekRepository.getProyekById(proyekId)
                if (response.isSuccessful) {
                    val proyek = response.body()
                    if (proyek != null) {
                        namaProyek.value = proyek.nama_proyek
                        deskripsiProyek.value = proyek.deskripsi_proyek
                        tanggalMulai.value = proyek.tanggal_mulai
                        tanggalBerakhir.value = proyek.tanggal_berakhir
                        statusProyek.value = proyek.status_proyek
                    }
                } else {
                    errorMessage.value = "Gagal memuat data proyek: ${response.message()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun updateProyek() {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            isSuccess.value = false

            try {
                val updatedProyek = Proyek(
                    id_proyek = proyekId,
                    nama_proyek = namaProyek.value,
                    deskripsi_proyek = deskripsiProyek.value,
                    tanggal_mulai = tanggalMulai.value,
                    tanggal_berakhir = tanggalBerakhir.value,
                    status_proyek = statusProyek.value
                )
                val response = proyekRepository.updateProyek(proyekId, updatedProyek)
                if (response.isSuccessful) {
                    isSuccess.value = true
                } else {
                    errorMessage.value = "Gagal memperbarui proyek: ${response.message()}"
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
        namaProyek.value = ""
        deskripsiProyek.value = ""
        tanggalMulai.value = ""
        tanggalBerakhir.value = ""
        statusProyek.value = "Aktif"
        isSuccess.value = false
        errorMessage.value = null
    }
}