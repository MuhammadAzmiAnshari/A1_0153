package com.example.projectmanagement.ui.view.proyek

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
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
import androidx.navigation.NavController
import com.example.projectmanagement.model.Proyek
import com.example.projectmanagement.model.Tugas
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
fun DetailProyekView(
    navController: NavController,
    navigateBack: () -> Unit,
    navigateToEdit: (Int) -> Unit,
    navigateToTambahTugas: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailProyekViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Mengambil state dari ViewModel
    val proyek by viewModel.proyek.collectAsState()
    val tugasList by viewModel.tugasList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    // Memuat detail proyek dan daftar tugas saat pertama kali tampil
    LaunchedEffect(Unit) {
        viewModel.loadProyekDetail()
        viewModel.loadTugasByProyekId()
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
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FloatingActionButton(
                    onClick = { navigateToEdit(proyek?.id_proyek ?: 0) },
                    shape = MaterialTheme.shapes.medium
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Proyek"
                    )
                }
                FloatingActionButton(
                    onClick = { navigateToTambahTugas(proyek?.id_proyek ?: 0) },
                    shape = MaterialTheme.shapes.medium
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Tambah Tugas"
                    )
                }
            }
        }

    ) { innerPadding ->
        BodyDetailProyek(
            proyek = proyek,
            tugasList = tugasList,
            isLoading = isLoading,
            errorMessage = errorMessage,
            modifier = Modifier.padding(innerPadding),
            onTugasClick = { tugasId ->
                navController.navigate("detail_tugas/$tugasId")
            }
        )
    }
}

@Composable
fun BodyDetailProyek(
    proyek: Proyek?,
    tugasList: List<Tugas>,
    isLoading: Boolean,
    errorMessage: String?,
    modifier: Modifier = Modifier,
    onTugasClick: (Int) -> Unit
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
                ItemDetailProyek(proyek = proyek)

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Daftar Tugas",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(tugasList) { tugas ->
                        TugasItem(
                            tugas = tugas,
                            onClick = { onTugasClick(tugas.id_tugas) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ItemDetailProyek(proyek: Proyek) {
    Card(
        modifier = Modifier
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

@Composable
fun TugasItem(
    tugas: Tugas,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nama Tugas: ${tugas.nama_tugas}", fontWeight = FontWeight.Bold)
            Text(text = "Deskripsi: ${tugas.deskripsi_tugas}")
            Text(text = "Prioritas: ${tugas.prioritas}")
            Text(text = "Status: ${tugas.status_tugas}")
        }
    }
}