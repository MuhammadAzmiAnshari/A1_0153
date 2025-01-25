package com.example.projectmanagement.repository

import com.example.projectmanagement.model.AnggotaTim
import com.example.projectmanagement.service.ApiService
import retrofit2.Response

interface AnggotaTimRepository {

    suspend fun getAllAnggotaTim(): Response<List<AnggotaTim>>

    suspend fun getAnggotaTimById(id: Int): Response<AnggotaTim>

    suspend fun createAnggotaTim(anggotaTim: AnggotaTim): Response<AnggotaTim>

    suspend fun updateAnggotaTim(id: Int, anggotaTim: AnggotaTim): Response<AnggotaTim>

    suspend fun deleteAnggotaTim(id: Int): Response<Void>
}

class NetworkAnggotaTimRepository(private val apiService: ApiService) : AnggotaTimRepository {

    override suspend fun getAllAnggotaTim(): Response<List<AnggotaTim>> {
        return apiService.getAllAnggotaTim()
    }

    override suspend fun getAnggotaTimById(id: Int): Response<AnggotaTim> {
        return apiService.getAnggotaTimById(id)
    }

    override suspend fun createAnggotaTim(anggotaTim: AnggotaTim): Response<AnggotaTim> {
        return apiService.createAnggotaTim(anggotaTim)
    }

    override suspend fun updateAnggotaTim(id: Int, anggotaTim: AnggotaTim): Response<AnggotaTim> {
        return apiService.updateAnggotaTim(id, anggotaTim)
    }

    override suspend fun deleteAnggotaTim(id: Int): Response<Void> {
        return apiService.deleteAnggotaTim(id)
    }
}