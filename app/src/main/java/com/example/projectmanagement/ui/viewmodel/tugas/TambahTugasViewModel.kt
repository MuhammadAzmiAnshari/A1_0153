package com.example.projectmanagement.ui.viewmodel.tugas

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectmanagement.model.Tugas
import com.example.projectmanagement.repository.TugasRepository
import kotlinx.coroutines.launch

class TambahTugasViewModel(
    private val tugasRepository: TugasRepository
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

    // Fungsi untuk menambahkan tugas
    fun tambahTugas(idProyek: Int, idTim: Int) {
        // Validasi input
        if (namaTugas.value.isEmpty() || deskripsiTugas.value.isEmpty() || prioritasTugas.value.isEmpty() || statusTugas.value.isEmpty()) {
            errorMessage.value = "Semua field wajib diisi!"
            return
        }

        // Membuat objek Tugas
        val tugas = Tugas(
            id_tugas = 0, // Nilai default, karena akan di-generate oleh database
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
                val response = tugasRepository.createTugas(tugas)
                if (response.isSuccessful) {
                    isSuccess.value = true
                } else {
                    errorMessage.value = "Gagal menambahkan tugas: ${response.message()}"
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