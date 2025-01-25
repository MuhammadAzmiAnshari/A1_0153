package com.example.projectmanagement.ui.viewmodel.tim

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectmanagement.model.Tim
import com.example.projectmanagement.repository.TimRepository
import kotlinx.coroutines.launch

class TambahTimViewModel(
    private val timRepository: TimRepository
) : ViewModel() {

    // State untuk input nama tim
    val namaTim = mutableStateOf("")

    // State untuk input deskripsi tim
    val deskripsiTim = mutableStateOf("")

    // State untuk loading
    val isLoading = mutableStateOf(false)

    // State untuk pesan sukses
    val isSuccess = mutableStateOf(false)

    // State untuk pesan error
    val errorMessage = mutableStateOf<String?>(null)

    // Fungsi untuk menambahkan tim baru
    fun tambahTim() {
        // Validasi input
        if (namaTim.value.isEmpty() || deskripsiTim.value.isEmpty()) {
            errorMessage.value = "Semua field harus diisi!"
            return
        }

        // Membuat objek Tim baru
        val tim = Tim(
            id_tim = 0, // ID akan di-generate oleh server
            nama_tim = namaTim.value,
            deskripsi_tim = deskripsiTim.value
        )

        // Mengirim data ke repository
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            isSuccess.value = false

            try {
                val response = timRepository.createTim(tim)
                if (response.isSuccessful) {
                    isSuccess.value = true
                } else {
                    errorMessage.value = "Gagal menambahkan tim: ${response.message()}"
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
