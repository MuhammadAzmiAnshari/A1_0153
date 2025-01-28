package com.example.projectmanagement.ui.view.tugas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.projectmanagement.ui.viewmodel.tugas.TambahTugasViewModel

object DestinasiTambahTugas : DestinasiNavigasi {
    override val route = "tambah_tugas"
    override val titleRes = "Tambah Tugas"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahTugasView(
    viewModel: TambahTugasViewModel = viewModel(factory = PenyediaViewModel.Factory),
    canNavigateBack: Boolean = false,
    navigateUp: () -> Unit = {},
    modifier: Modifier = Modifier,
    proyekId: Int? = null // Tambahkan parameter proyekId
) {
    // State dari ViewModel
    val namaTugas by viewModel.namaTugas
    val deskripsiTugas by viewModel.deskripsiTugas
    val prioritasTugas by viewModel.prioritasTugas
    val statusTugas by viewModel.statusTugas
    val isLoading by viewModel.isLoading
    val isSuccess by viewModel.isSuccess
    val errorMessage by viewModel.errorMessage

    // Reset form jika berhasil menambahkan tugas
    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            viewModel.resetForm()
        }
    }

    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = "Tambah Tugas",
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

            // Tombol Tambah Tugas
            Button(
                onClick = {
                    if (proyekId != null) {
                        viewModel.tambahTugas(idProyek = proyekId, idTim = 1) // Ganti idTim dengan nilai yang sesuai
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    Text("Menambahkan...")
                } else {
                    Text("Tambah Tugas")
                }
            }

            // Tampilkan pesan sukses atau error
            if (isSuccess) {
                Text(
                    text = "Tugas berhasil ditambahkan!",
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