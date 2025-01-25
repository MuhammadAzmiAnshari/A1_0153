package com.example.projectmanagement.ui.view.proyek

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectmanagement.model.Proyek
import com.example.projectmanagement.ui.CostumeTopAppBar
import com.example.projectmanagement.ui.navigasi.DestinasiNavigasi
import com.example.projectmanagement.ui.viewmodel.PenyediaViewModel
import com.example.projectmanagement.viewmodel.DetailProyekViewModel

object DestinasiDetailProyek : DestinasiNavigasi {
    override val route = "detail_proyek"
    const val PROYEK_ID = "proyekId"
    override val titleRes = "Detail Proyek"
    val routeWithArg = "$route/{$PROYEK_ID}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailProyekView( // Ubah nama dari DetailProyekScreen menjadi DetailProyekView
    navigateBack: () -> Unit,
    navigateToEdit: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailProyekViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Mengambil state dari ViewModel
    val proyek by viewModel.proyek.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    // Memuat detail proyek saat pertama kali tampil
    LaunchedEffect(Unit) {
        viewModel.loadProyekDetail()
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailProyek.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEdit(proyek?.id_proyek ?: 0) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Proyek"
                )
            }
        }
    ) { innerPadding ->
        BodyDetailProyek(
            proyek = proyek,
            isLoading = isLoading,
            errorMessage = errorMessage,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BodyDetailProyek(
    proyek: Proyek?,
    isLoading: Boolean,
    errorMessage: String?,
    modifier: Modifier = Modifier
) {
    when {
        isLoading -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        errorMessage != null -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = errorMessage,
                    color = Color.Red
                )
            }
        }
        proyek != null -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailProyek(
                    proyek = proyek,
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun ItemDetailProyek(
    modifier: Modifier = Modifier,
    proyek: Proyek
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            ComponentDetailProyek(judul = "Nama Proyek", isinya = proyek.nama_proyek)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailProyek(judul = "Deskripsi", isinya = proyek.deskripsi_proyek)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailProyek(judul = "Tanggal Mulai", isinya = proyek.tanggal_mulai)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailProyek(judul = "Tanggal Berakhir", isinya = proyek.tanggal_berakhir)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailProyek(judul = "Status", isinya = proyek.status_proyek)
        }
    }
}

@Composable
fun ComponentDetailProyek(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}