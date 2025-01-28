package com.example.projectmanagement.ui.view.proyek

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectmanagement.ui.CostumeTopAppBar
import com.example.projectmanagement.ui.navigasi.DestinasiNavigasi
import com.example.projectmanagement.ui.viewmodel.PenyediaViewModel
import com.example.projectmanagement.ui.viewmodel.proyek.UpdateProyekViewModel

object DestinasiUpdateProyek: DestinasiNavigasi {
    override val route = "update_proyek/{proyekId}"
    override val titleRes = "Update Proyek"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProyekView(
    proyekId: Int,
    viewModel: UpdateProyekViewModel = viewModel(factory = PenyediaViewModel.Factory),
    canNavigateBack: Boolean = false,
    navigateUp: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // State dari ViewModel
    val namaProyek by viewModel.namaProyek
    val deskripsiProyek by viewModel.deskripsiProyek
    val tanggalMulai by viewModel.tanggalMulai
    val tanggalBerakhir by viewModel.tanggalBerakhir
    val statusProyek by viewModel.statusProyek
    val isLoading by viewModel.isLoading
    val isSuccess by viewModel.isSuccess
    val errorMessage by viewModel.errorMessage

    // Memuat data proyek saat pertama kali tampil
    LaunchedEffect(proyekId) {
        viewModel.loadProyekData() // Panggil tanpa parameter
    }

    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = "Update Proyek",
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
            // Input Nama Proyek
            OutlinedTextField(
                value = namaProyek,
                onValueChange = { viewModel.namaProyek.value = it },
                label = { Text("Nama Proyek") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Input Deskripsi Proyek
            OutlinedTextField(
                value = deskripsiProyek,
                onValueChange = { viewModel.deskripsiProyek.value = it },
                label = { Text("Deskripsi Proyek") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Input Tanggal Mulai
            OutlinedTextField(
                value = tanggalMulai,
                onValueChange = { viewModel.tanggalMulai.value = it },
                label = { Text("Tanggal Mulai (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Input Tanggal Berakhir
            OutlinedTextField(
                value = tanggalBerakhir,
                onValueChange = { viewModel.tanggalBerakhir.value = it },
                label = { Text("Tanggal Berakhir (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Input Status Proyek
            Text("Status Proyek")
            val statusOptions = listOf("Aktif", "Tidak Aktif")
            Column {
                statusOptions.forEach { option ->
                    Row(
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        RadioButton(
                            selected = (statusProyek == option),
                            onClick = { viewModel.statusProyek.value = option }
                        )
                        Text(text = option, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Tombol Update Proyek
            Button(
                onClick = { viewModel.updateProyek() }, // Panggil tanpa parameter
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Text("Update Proyek")
                }
            }

            // Tampilkan pesan sukses atau error
            if (isSuccess) {
                Text(
                    text = "Proyek berhasil diperbarui!",
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