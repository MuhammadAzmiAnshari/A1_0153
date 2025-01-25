package com.example.projectmanagement.model

import kotlinx.serialization.Serializable

@Serializable
data class Proyek(
    val id_proyek: Int,
    val nama_proyek: String,
    val deskripsi_proyek: String,
    val tanggal_mulai: String,
    val tanggal_berakhir: String,
    val status_proyek: String
)