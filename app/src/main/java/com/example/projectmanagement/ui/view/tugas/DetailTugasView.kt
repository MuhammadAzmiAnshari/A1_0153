package com.example.projectmanagement.ui.view.tugas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectmanagement.model.Tugas
import com.example.projectmanagement.ui.CostumeTopAppBar
import com.example.projectmanagement.ui.navigasi.DestinasiNavigasi
import com.example.projectmanagement.ui.viewmodel.PenyediaViewModel
import com.example.projectmanagement.ui.viewmodel.tugas.DetailTugasViewModel

object DestinasiDetailTugas : DestinasiNavigasi {
    override val route = "detail_tugas/{tugasId}"
    override val titleRes = "Detail Tugas"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTugasView(
    tugasId: Int, // Terima tugasId sebagai parameter
    viewModel: DetailTugasViewModel = viewModel(factory = PenyediaViewModel.Factory),
    canNavigateBack: Boolean = false,
    navigateUp: () -> Unit = {},
    onDeleteTugas: () -> Unit,
    onUpdateTugas: () -> Unit,
    modifier: Modifier = Modifier
) {
    // State dari ViewModel
    val tugas by viewModel.tugas
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    // Memuat data tugas saat pertama kali tampil
    LaunchedEffect(tugasId) {
        viewModel.loadTugas(tugasId)
    }

    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = "Detail Tugas",
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
            // Tampilkan loading indicator
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                )
            }

            // Tampilkan pesan error
            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }

            // Tampilkan detail tugas
            if (tugas != null) {
                TugasDetailItem(
                    tugas = tugas!!,
                    onDelete = { viewModel.deleteTugas(tugasId); onDeleteTugas() },
                    onUpdate = onUpdateTugas
                )
            } else if (!isLoading) {
                Text(
                    text = "Tugas tidak ditemukan.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun TugasDetailItem(
    tugas: Tugas,
    onDelete: () -> Unit,
    onUpdate: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = tugas.nama_tugas,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = tugas.deskripsi_tugas,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Status: ${tugas.status_tugas}",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onDelete,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Hapus Tugas")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onUpdate,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Update Tugas")
            }
        }
    }
}