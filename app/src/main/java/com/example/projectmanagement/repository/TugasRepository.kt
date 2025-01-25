package com.example.projectmanagement.repository

import com.example.projectmanagement.model.Tugas
import com.example.projectmanagement.service.ApiService
import retrofit2.Response

interface TugasRepository {

    suspend fun getAllTugas(): Response<List<Tugas>>

    suspend fun getTugasById(id: Int): Response<Tugas>

    suspend fun createTugas(tugas: Tugas): Response<Tugas>

    suspend fun updateTugas(id: Int, tugas: Tugas): Response<Tugas>

    suspend fun deleteTugas(id: Int): Response<Void>

    suspend fun getTugasByProyekId(proyekId: Int): Response<List<Tugas>>
}

class NetworkTugasRepository(private val apiService: ApiService) : TugasRepository {

    override suspend fun getAllTugas(): Response<List<Tugas>> {
        return apiService.getAllTugas()
    }

    override suspend fun getTugasById(id: Int): Response<Tugas> {
        return apiService.getTugasById(id)
    }

    override suspend fun createTugas(tugas: Tugas): Response<Tugas> {
        return apiService.createTugas(tugas)
    }

    override suspend fun updateTugas(id: Int, tugas: Tugas): Response<Tugas> {
        return apiService.updateTugas(id, tugas)
    }

    override suspend fun deleteTugas(id: Int): Response<Void> {
        return apiService.deleteTugas(id)
    }

    override suspend fun getTugasByProyekId(proyekId: Int): Response<List<Tugas>> {
        return apiService.getTugasByProyekId(proyekId)
    }
}