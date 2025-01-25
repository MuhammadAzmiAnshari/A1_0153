package com.example.projectmanagement.ui.view.proyek

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectmanagement.model.Proyek
import com.example.projectmanagement.ui.CostumeTopAppBar
import com.example.projectmanagement.ui.navigasi.DestinasiNavigasi
import com.example.projectmanagement.ui.viewmodel.PenyediaViewModel
import com.example.projectmanagement.ui.viewmodel.proyek.HomeProyekViewModel

object DestinasiHomeProyek: DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Home"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeProyekView(
    viewModel: HomeProyekViewModel = viewModel(factory = PenyediaViewModel.Factory),
    canNavigateBack: Boolean = false,
    navigateUp: () -> Unit = {},
    onDetailProyekClick: (Int) -> Unit,
    onTambahProyekClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // ViewModel
    val proyekList by viewModel.proyekList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    // Memuat data saat pertama kali tampil
    LaunchedEffect(Unit) {
        viewModel.loadProyek()
    }

    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = "Daftar Proyek",
                canNavigateBack = canNavigateBack,
                navigateUp = navigateUp,
                onRefresh = { viewModel.loadProyek() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onTambahProyekClick) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Proyek")
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Tampilkan loading indicator
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .wrapContentWidth()
                )
            }

            // Tampilkan pesan error
            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Tampilkan daftar proyek
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(proyekList) { proyek ->
                    ProyekItem(
                        proyek = proyek,
                        onDelete = { viewModel.deleteProyek(proyek.id_proyek) },
                        onDetail = { onDetailProyekClick(proyek.id_proyek) }
                    )
                }
            }
        }
    }
}

@Composable
fun ProyekItem(
    proyek: Proyek,
    onDelete: () -> Unit,
    onDetail: () -> Unit
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
                text = proyek.nama_proyek,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = proyek.deskripsi_proyek,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Status: ${proyek.status_proyek}",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onDelete,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Hapus Proyek")
                }
                Button(
                    onClick = onDetail, // Tombol untuk melihat detail proyek
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                ) {
                    Text("Detail Proyek")
                }
            }
        }
    }
}