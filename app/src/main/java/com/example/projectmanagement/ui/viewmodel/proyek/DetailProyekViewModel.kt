package com.example.projectmanagement.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectmanagement.model.Proyek
import com.example.projectmanagement.model.Tugas
import com.example.projectmanagement.repository.ProyekRepository
import com.example.projectmanagement.repository.TugasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class DetailProyekViewModel (
    private val proyekRepository: ProyekRepository,
    private val tugasRepository: TugasRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // ID proyek yang diambil dari SavedStateHandle
    private val proyekId: Int = savedStateHandle.get<Int>("proyekId") ?: 0

    // StateFlow untuk menyimpan detail proyek
    private val _proyek = MutableStateFlow<Proyek?>(null)
    val proyek: StateFlow<Proyek?> get() = _proyek

    // StateFlow untuk menyimpan daftar tugas
    private val _tugasList = MutableStateFlow<List<Tugas>>(emptyList())
    val tugasList: StateFlow<List<Tugas>> get() = _tugasList

    // StateFlow untuk menyimpan status loading
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    // StateFlow untuk menyimpan pesan error
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    // Fungsi untuk memuat detail proyek dari repository
    fun loadProyekDetail() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response: Response<Proyek> = proyekRepository.getProyekById(proyekId)
                if (response.isSuccessful) {
                    _proyek.value = response.body()
                } else {
                    _errorMessage.value = "Gagal memuat detail proyek: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadTugasByProyekId() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response: Response<List<Tugas>> = tugasRepository.getTugasByProyekId(proyekId)
                if (response.isSuccessful) {
                    _tugasList.value = response.body() ?: emptyList()
                } else {
                    _errorMessage.value = "Gagal memuat daftar tugas: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Fungsi untuk memperbarui proyek
    fun updateProyek(proyek: Proyek) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response: Response<Proyek> = proyekRepository.updateProyek(proyekId, proyek)
                if (response.isSuccessful) {
                    _proyek.value = response.body()
                } else {
                    _errorMessage.value = "Gagal memperbarui proyek: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}