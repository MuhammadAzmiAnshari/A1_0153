package com.example.projectmanagement.repository

import com.example.projectmanagement.model.Tim
import com.example.projectmanagement.service.ApiService
import retrofit2.Response

interface TimRepository {

    suspend fun getAllTim(): Response<List<Tim>>

    suspend fun getTimById(id: Int): Response<Tim>

    suspend fun createTim(tim: Tim): Response<Tim>

    suspend fun updateTim(id: Int, tim: Tim): Response<Tim>

    suspend fun deleteTim(id: Int): Response<Void>
}

class NetworkTimRepository(private val apiService: ApiService) : TimRepository {

    override suspend fun getAllTim(): Response<List<Tim>> {
        return apiService.getAllTim()
    }

    override suspend fun getTimById(id: Int): Response<Tim> {
        return apiService.getTimById(id)
    }

    override suspend fun createTim(tim: Tim): Response<Tim> {
        return apiService.createTim(tim)
    }

    override suspend fun updateTim(id: Int, tim: Tim): Response<Tim> {
        return apiService.updateTim(id, tim)
    }

    override suspend fun deleteTim(id: Int): Response<Void> {
        return apiService.deleteTim(id)
    }
}