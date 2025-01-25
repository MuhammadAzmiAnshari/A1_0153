package com.example.projectmanagement.model

import kotlinx.serialization.Serializable

@Serializable
data class Tim(
    val id_tim: Int,
    val nama_tim: String,
    val deskripsi_tim: String
)