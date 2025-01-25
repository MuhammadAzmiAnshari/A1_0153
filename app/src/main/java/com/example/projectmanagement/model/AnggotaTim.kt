package com.example.projectmanagement.model

import kotlinx.serialization.Serializable

@Serializable

data class AnggotaTim(
    val id_anggota: Int,
    val id_tim: Int,
    val nama_anggota: String,
    val peran: String // Pemimpin, Anggota
)

