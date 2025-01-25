package com.example.projectmanagement.ui.viewmodel.proyek

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectmanagement.model.Proyek
import com.example.projectmanagement.repository.ProyekRepository
import kotlinx.coroutines.launch

class TambahProyekViewModel(
    private val proyekRepository: ProyekRepository
) : ViewModel() {

    // State untuk input nama proyek
    val namaProyek = mutableStateOf("")

    // State untuk input deskripsi proyek
    val deskripsiProyek = mutableStateOf("")

    // State untuk tanggal mulai proyek
    val tanggalMulai = mutableStateOf("")

    // State untuk tanggal berakhir proyek
    val tanggalBerakhir = mutableStateOf("")

    // State untuk status proyek
    val statusProyek = mutableStateOf("Aktif")

    // State untuk loading
    val isLoading = mutableStateOf(false)

    // State untuk pesan sukses
    val isSuccess = mutableStateOf(false)

    // State untuk pesan error
    val errorMessage = mutableStateOf<String?>(null)

    // Fungsi untuk menambahkan proyek baru
    fun tambahProyek() {
        // Validasi input
        if (namaProyek.value.isEmpty() || deskripsiProyek.value.isEmpty() ||
            tanggalMulai.value.isEmpty() || tanggalBerakhir.value.isEmpty()
        ) {
            errorMessage.value = "Semua field harus diisi!"
            return
        }

        // Membuat objek Proyek baru
        val proyek = Proyek(
            id_proyek = 0, // ID akan di-generate oleh server
            nama_proyek = namaProyek.value,
            deskripsi_proyek = deskripsiProyek.value,
            tanggal_mulai = tanggalMulai.value,
            tanggal_berakhir = tanggalBerakhir.value,
            status_proyek = statusProyek.value
        )

        // Mengirim data ke repository
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            isSuccess.value = false

            try {
                val response = proyekRepository.createProyek(proyek)
                if (response.isSuccessful) {
                    isSuccess.value = true
                } else {
                    errorMessage.value = "Gagal menambahkan proyek: ${response.message()}"
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