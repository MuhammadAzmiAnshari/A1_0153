package com.example.projectmanagement.ui.view.tim

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectmanagement.model.Tim
import com.example.projectmanagement.ui.CostumeTopAppBar
import com.example.projectmanagement.ui.navigasi.DestinasiNavigasi
import com.example.projectmanagement.ui.viewmodel.PenyediaViewModel
import com.example.projectmanagement.ui.viewmodel.tim.HomeTimViewModel

object DestinasiHomeTim : DestinasiNavigasi {
    override val route = "home_tim"
    override val titleRes = "Daftar Tim"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTimView(
    viewModel: HomeTimViewModel = viewModel(factory = PenyediaViewModel.Factory),
    canNavigateBack: Boolean = false,
    navigateUp: () -> Unit = {},
    onTambahTimClick: () -> Unit,
    onDetailTimClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // State dari ViewModel
    val timList by viewModel.timList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    // Memuat data tim saat pertama kali tampil
    LaunchedEffect(Unit) {
        viewModel.loadTim()
    }

    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeTim.titleRes,
                canNavigateBack = canNavigateBack,
                navigateUp = navigateUp
            )
        },
        floatingActionButton = {
            // Tombol untuk menambah tim
            Button(
                onClick = onTambahTimClick,
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Tambah Tim")
            }
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

            // Tampilkan daftar tim
            if (timList.isEmpty() && !isLoading) {
                Text(
                    text = "Tidak ada tim yang tersedia.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(timList) { tim ->
                        TimItem(
                            tim = tim,
                            onDelete = { viewModel.deleteTim(tim.id_tim) },
                            onDetailClick = { onDetailTimClick(tim.id_tim) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TimItem(
    tim: Tim,
    onDelete: () -> Unit,
    onDetailClick: () -> Unit
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
                text = tim.nama_tim,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = tim.deskripsi_tim,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onDelete,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Hapus Tim")
            }
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onDetailClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Detail Tim")
            }

        }
    }
}