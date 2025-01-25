package com.example.projectmanagement.ui.viewmodel.tugas

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectmanagement.model.Tugas
import com.example.projectmanagement.repository.TugasRepository
import kotlinx.coroutines.launch

class UpdateTugasViewModel(
    private val tugasRepository: TugasRepository,
    private val tugasId: Int // ID tugas yang akan diperbarui
) : ViewModel() {

    // State untuk input nama tugas
    val namaTugas = mutableStateOf("")

    // State untuk input deskripsi tugas
    val deskripsiTugas = mutableStateOf("")

    // State untuk input prioritas tugas
    val prioritasTugas = mutableStateOf("")

    // State untuk input status tugas
    val statusTugas = mutableStateOf("")

    // State untuk loading
    val isLoading = mutableStateOf(false)

    // State untuk pesan sukses
    val isSuccess = mutableStateOf(false)

    // State untuk pesan error
    val errorMessage = mutableStateOf<String?>(null)

    // Fungsi untuk memuat data tugas yang akan diperbarui
    fun loadTugasData() {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            try {
                val response = tugasRepository.getTugasById(tugasId)
                if (response.isSuccessful) {
                    val tugas = response.body()
                    if (tugas != null) {
                        namaTugas.value = tugas.nama_tugas
                        deskripsiTugas.value = tugas.deskripsi_tugas
                        prioritasTugas.value = tugas.prioritas
                        statusTugas.value = tugas.status_tugas
                    }
                } else {
                    errorMessage.value = "Gagal memuat data tugas: ${response.message()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    // Fungsi untuk memperbarui tugas
    fun updateTugas(idProyek: Int, idTim: Int) {
        // Validasi input
        if (namaTugas.value.isEmpty() || deskripsiTugas.value.isEmpty() || prioritasTugas.value.isEmpty() || statusTugas.value.isEmpty()) {
            errorMessage.value = "Semua field wajib diisi!"
            return
        }

        // Membuat objek Tugas yang diperbarui
        val updatedTugas = Tugas(
            id_tugas = tugasId,
            id_proyek = idProyek,
            id_tim = idTim,
            nama_tugas = namaTugas.value,
            deskripsi_tugas = deskripsiTugas.value,
            prioritas = prioritasTugas.value,
            status_tugas = statusTugas.value
        )

        // Mengirim data ke repository
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            try {
                val response = tugasRepository.updateTugas(tugasId, updatedTugas)
                if (response.isSuccessful) {
                    isSuccess.value = true
                } else {
                    errorMessage.value = "Gagal memperbarui tugas: ${response.message()}"
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
        namaTugas.value = ""
        deskripsiTugas.value = ""
        prioritasTugas.value = ""
        statusTugas.value = ""
        isSuccess.value = false
        errorMessage.value = null
    }
}