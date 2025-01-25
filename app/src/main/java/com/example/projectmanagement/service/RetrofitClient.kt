package com.example.myapp.network

import com.example.projectmanagement.repository.AnggotaTimRepository
import com.example.projectmanagement.repository.NetworkAnggotaTimRepository
import com.example.projectmanagement.repository.NetworkProyekRepository
import com.example.projectmanagement.repository.NetworkTimRepository
import com.example.projectmanagement.repository.NetworkTugasRepository
import com.example.projectmanagement.repository.ProyekRepository
import com.example.projectmanagement.repository.TimRepository
import com.example.projectmanagement.repository.TugasRepository
import com.example.projectmanagement.service.ApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

// Interface untuk Dependency Injection
interface AppContainer {
    val proyekRepository: ProyekRepository
    val tugasRepository: TugasRepository
    val timRepository: TimRepository
    val anggotaTimRepository: AnggotaTimRepository
}

// Implementasi container untuk Retrofit dan Repository
class RetrofitContainer : AppContainer {

    // Base URL dari API Anda
    private val baseUrl = "http://10.0.2.2:3000/"

    // Konfigurasi Kotlinx Serialization
    private val json = Json {
        ignoreUnknownKeys = true // Mengabaikan key yang tidak dikenal di JSON
    }

    // Instance Retrofit (tanpa OkHttpClient)
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType())) // Konverter Kotlinx Serialization
            .build()
    }

    // Instance ApiService
    private val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    // Implementasi Repository
    override val proyekRepository: ProyekRepository by lazy {
        NetworkProyekRepository(apiService)
    }

    override val tugasRepository: TugasRepository by lazy {
        NetworkTugasRepository(apiService)
    }

    override val timRepository: TimRepository by lazy {
        NetworkTimRepository(apiService)
    }

    override val anggotaTimRepository: AnggotaTimRepository by lazy {
        NetworkAnggotaTimRepository(apiService)
    }
}