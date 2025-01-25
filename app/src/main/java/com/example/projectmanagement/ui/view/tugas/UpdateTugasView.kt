package com.example.projectmanagement.ui.view.tugas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectmanagement.ui.CostumeTopAppBar
import com.example.projectmanagement.ui.navigasi.DestinasiNavigasi
import com.example.projectmanagement.ui.viewmodel.PenyediaViewModel
import com.example.projectmanagement.ui.viewmodel.tugas.UpdateTugasViewModel

object DestinasiUpdateTugas : DestinasiNavigasi {
    override val route = "update_tugas/{tugasId}"
    override val titleRes = "Update Tugas"
}

@OptIn(ExperimentalMaterial3Api::class) // Tambahkan anotasi ini
@Composable
fun UpdateTugasView(
    tugasId: Int, // ID tugas yang akan diperbarui
    viewModel: UpdateTugasViewModel = viewModel(factory = PenyediaViewModel.Factory),
    canNavigateBack: Boolean = false,
    navigateUp: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // State dari ViewModel
    val namaTugas by viewModel.namaTugas
    val deskripsiTugas by viewModel.deskripsiTugas
    val prioritasTugas by viewModel.prioritasTugas
    val statusTugas by viewModel.statusTugas
    val isLoading by viewModel.isLoading
    val isSuccess by viewModel.isSuccess
    val errorMessage by viewModel.errorMessage

    // Memuat data tugas saat pertama kali tampil
    LaunchedEffect(tugasId) {
        viewModel.loadTugasData()
    }

    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = "Update Tugas",
                canNavigateBack = canNavigateBack,
                navigateUp = navigateUp
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Input Nama Tugas
            OutlinedTextField(
                value = namaTugas,
                onValueChange = { viewModel.namaTugas.value = it },
                label = { Text("Nama Tugas") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Input Deskripsi Tugas
            OutlinedTextField(
                value = deskripsiTugas,
                onValueChange = { viewModel.deskripsiTugas.value = it },
                label = { Text("Deskripsi Tugas") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Input Prioritas Tugas
            OutlinedTextField(
                value = prioritasTugas,
                onValueChange = { viewModel.prioritasTugas.value = it },
                label = { Text("Prioritas Tugas") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Input Status Tugas
            OutlinedTextField(
                value = statusTugas,
                onValueChange = { viewModel.statusTugas.value = it },
                label = { Text("Status Tugas") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Tombol Update Tugas
            Button(
                onClick = { viewModel.updateTugas(idProyek = 1, idTim = 1) }, // Ganti dengan idProyek dan idTim yang sesuai
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    Text("Memperbarui...")
                } else {
                    Text("Update Tugas")
                }
            }

            // Tampilkan pesan sukses atau error
            if (isSuccess) {
                Text(
                    text = "Tugas berhasil diperbarui!",
                    color = Color.Green,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}