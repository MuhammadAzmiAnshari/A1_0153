package com.example.projectmanagement.ui.view.tim

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectmanagement.ui.CostumeTopAppBar
import com.example.projectmanagement.ui.navigasi.DestinasiNavigasi
import com.example.projectmanagement.ui.viewmodel.PenyediaViewModel
import com.example.projectmanagement.ui.viewmodel.tim.TambahTimViewModel

object DestinasiTambahTim : DestinasiNavigasi {
    override val route = "tambah_tim"
    override val titleRes = "Tambah Tim"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahTimView(
    viewModel: TambahTimViewModel = viewModel(factory = PenyediaViewModel.Factory),
    canNavigateBack: Boolean = false,
    navigateUp: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // State dari ViewModel
    val namaTim by viewModel.namaTim
    val deskripsiTim by viewModel.deskripsiTim
    val isLoading by viewModel.isLoading
    val isSuccess by viewModel.isSuccess
    val errorMessage by viewModel.errorMessage

    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiTambahTim.titleRes,
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
            // Input Nama Tim
            OutlinedTextField(
                value = namaTim,
                onValueChange = { viewModel.namaTim.value = it },
                label = { Text("Nama Tim") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Input Deskripsi Tim
            OutlinedTextField(
                value = deskripsiTim,
                onValueChange = { viewModel.deskripsiTim.value = it },
                label = { Text("Deskripsi Tim") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Tombol Tambah Tim
            Button(
                onClick = { viewModel.tambahTim() },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Text("Tambah Tim")
                }
            }

            // Tampilkan pesan sukses atau error
            if (isSuccess) {
                Text(
                    text = "Tim berhasil ditambahkan!",
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