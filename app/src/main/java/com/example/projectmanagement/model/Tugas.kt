package com.example.projectmanagement.model

import kotlinx.serialization.Serializable

@Serializable
data class Tugas(
    val id_tugas: Int,
    val id_proyek: Int,
    val id_tim: Int,
    val nama_tugas: String,
    val deskripsi_tugas: String,
    val prioritas: String, // Rendah, Sedang, Tinggi
    val status_tugas: String // Belum Mulai, Sedang Berlangsung, Selesai
)