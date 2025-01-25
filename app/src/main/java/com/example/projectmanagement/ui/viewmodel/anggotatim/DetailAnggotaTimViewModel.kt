package com.example.projectmanagement.ui.viewmodel.anggotatim

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectmanagement.model.AnggotaTim
import com.example.projectmanagement.repository.AnggotaTimRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class DetailAnggotaTimViewModel(
    private val anggotaTimRepository: AnggotaTimRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // ID anggota tim yang diambil dari SavedStateHandle
    private val anggotaId: Int = savedStateHandle.get<Int>("anggotaId") ?: 0

    // StateFlow untuk menyimpan detail anggota tim
    private val _anggotaTim = MutableStateFlow<AnggotaTim?>(null)
    val anggotaTim: StateFlow<AnggotaTim?> get() = _anggotaTim

    // StateFlow untuk menyimpan status loading
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    // StateFlow untuk menyimpan pesan error
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    // Fungsi untuk memuat detail anggota tim dari repository
    fun loadAnggotaTimDetail() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response: Response<AnggotaTim> = anggotaTimRepository.getAnggotaTimById(anggotaId)
                if (response.isSuccessful) {
                    _anggotaTim.value = response.body()
                } else {
                    _errorMessage.value = "Gagal memuat detail anggota tim: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Fungsi untuk memperbarui anggota tim
    fun updateAnggotaTim(anggota: AnggotaTim) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response: Response<AnggotaTim> = anggotaTimRepository.updateAnggotaTim(anggotaId, anggota)
                if (response.isSuccessful) {
                    _anggotaTim.value = response.body()
                } else {
                    _errorMessage.value = "Gagal memperbarui anggota tim: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Fungsi untuk menghapus anggota tim
    fun deleteAnggotaTim() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response: Response<Void> = anggotaTimRepository.deleteAnggotaTim(anggotaId)
                if (response.isSuccessful) {
                    _anggotaTim.value = null // Reset data setelah penghapusan
                } else {
                    _errorMessage.value = "Gagal menghapus anggota tim: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}