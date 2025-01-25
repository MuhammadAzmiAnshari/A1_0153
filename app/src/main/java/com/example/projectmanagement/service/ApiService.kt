package com.example.projectmanagement.service

import com.example.projectmanagement.model.AnggotaTim
import com.example.projectmanagement.model.Proyek
import com.example.projectmanagement.model.Tim
import com.example.projectmanagement.model.Tugas
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    //////////////////////////////////////////////////////////////////////////////////////////////// Proyek Endpoints
    @GET("proyek/")
    suspend fun getAllProyek(): Response<List<Proyek>>

    @GET("proyek/{id}")
    suspend fun getProyekById(@Path("id") id: Int): Response<Proyek>

    @POST("proyek/tambahproyek")
    suspend fun createProyek(@Body proyek: Proyek): Response<Proyek>

    @PUT("proyek/{id}")
    suspend fun updateProyek(@Path("id") id: Int, @Body proyek: Proyek): Response<Proyek>

    @DELETE("proyek/{id}")
    suspend fun deleteProyek(@Path("id") id: Int): Response<Void>


    //////////////////////////////////////////////////////////////////////////////////////////////// Tugas Endpoints
    @GET("tugas")
    suspend fun getAllTugas(): Response<List<Tugas>>

    @GET("tugas/{id}")
    suspend fun getTugasById(@Path("id") id: Int): Response<Tugas>

    @POST("tugas/tambahtugas")
    suspend fun createTugas(@Body tugas: Tugas): Response<Tugas>

    @PUT("tugas/{id}")
    suspend fun updateTugas(@Path("id") id: Int, @Body tugas: Tugas): Response<Tugas>

    @DELETE("tugas/{id}")
    suspend fun deleteTugas(@Path("id") id: Int): Response<Void>

    @GET("tugas/proyek/{proyekId}")
    suspend fun getTugasByProyekId(@Path("proyekId") proyekId: Int): Response<List<Tugas>>


    //////////////////////////////////////////////////////////////////////////////////////////////// Tim Endpoints
    @GET("tim")
    suspend fun getAllTim(): Response<List<Tim>>

    @GET("tim/{id}")
    suspend fun getTimById(@Path("id") id: Int): Response<Tim>

    @POST("tim")
    suspend fun createTim(@Body tim: Tim): Response<Tim>

    @PUT("tim/{id}")
    suspend fun updateTim(@Path("id") id: Int, @Body tim: Tim): Response<Tim>

    @DELETE("tim/{id}")
    suspend fun deleteTim(@Path("id") id: Int): Response<Void>


    //////////////////////////////////////////////////////////////////////////////////////////////// AnggotaTim Endpoints
    @GET("anggotatim")
    suspend fun getAllAnggotaTim(): Response<List<AnggotaTim>>

    @GET("anggotatim/{id}")
    suspend fun getAnggotaTimById(@Path("id") id: Int): Response<AnggotaTim>

    @POST("anggotatim")
    suspend fun createAnggotaTim(@Body anggotaTim: AnggotaTim): Response<AnggotaTim>

    @PUT("anggotatim/{id}")
    suspend fun updateAnggotaTim(@Path("id") id: Int, @Body anggotaTim: AnggotaTim): Response<AnggotaTim>

    @DELETE("anggotatim/{id}")
    suspend fun deleteAnggotaTim(@Path("id") id: Int): Response<Void>
}