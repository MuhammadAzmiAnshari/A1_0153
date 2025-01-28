package com.example.projectmanagement.repository

import com.example.projectmanagement.model.Proyek
import com.example.projectmanagement.model.Tugas
import com.example.projectmanagement.service.ApiService
import retrofit2.Response

interface ProyekRepository {

    suspend fun getAllProyek(): Response<List<Proyek>>
    suspend fun getProyekById(proyekId: Int): Response<Proyek>
    suspend fun createProyek(proyek: Proyek): Response<Proyek>
    suspend fun updateProyek(proyekId: Int, proyek: Proyek): Response<Proyek>
    suspend fun deleteProyek(proyekId: Int): Response<Void>

}

class NetworkProyekRepository(private val apiService: ApiService) : ProyekRepository {

    override suspend fun getAllProyek(): Response<List<Proyek>> {
        return apiService.getAllProyek()
    }

    override suspend fun getProyekById(proyekId: Int): Response<Proyek> {
        return apiService.getProyekById(proyekId)
    }

    override suspend fun createProyek(proyek: Proyek): Response<Proyek> {
        return apiService.createProyek(proyek)
    }

    override suspend fun updateProyek(proyekId: Int, proyek: Proyek): Response<Proyek> {
        return apiService.updateProyek(proyekId, proyek)
    }

    override suspend fun deleteProyek(proyekId: Int): Response<Void> {
        return apiService.deleteProyek(proyekId)
    }


}

