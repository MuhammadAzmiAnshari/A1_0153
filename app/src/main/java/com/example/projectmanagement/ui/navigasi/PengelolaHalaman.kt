package com.example.projectmanagement.ui.navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projectmanagement.ui.view.proyek.DetailProyekView
import com.example.projectmanagement.ui.view.proyek.HomeProyekView
import com.example.projectmanagement.ui.view.proyek.TambahProyekView
import com.example.projectmanagement.ui.view.proyek.UpdateProyekView
import com.example.projectmanagement.ui.view.tugas.DetailTugasView
import com.example.projectmanagement.ui.view.tugas.TambahTugasView
import com.example.projectmanagement.ui.view.tugas.UpdateTugasView

@Composable
fun PengelolaHalaman(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeProyek.route, // Ubah startDestination ke HomeProyekView
        modifier = modifier
    ) {
        // Rute untuk HomeProyekView
        composable(route = DestinasiHomeProyek.route) {
            HomeProyekView(
                onTambahProyekClick = { navController.navigate(DestinasiTambahProyek.route) },
                onDetailProyekClick = { proyekId ->
                    navController.navigate("${DestinasiDetailProyek.route}/$proyekId")
                },
                canNavigateBack = true,
                navigateUp = { navController.navigateUp() }
            )
        }

        // Rute untuk TambahProyekView
        composable(route = DestinasiTambahProyek.route) {
            TambahProyekView(
                canNavigateBack = true,
                navigateUp = { navController.navigateUp() }
            )
        }

        // Rute untuk UpdateProyekView
        composable(
            route = "${DestinasiUpdateProyek.route}/{proyekId}",
            arguments = listOf(navArgument("proyekId") { type = NavType.IntType })
        ) { backStackEntry ->
            val proyekId = backStackEntry.arguments?.getInt("proyekId") ?: 0
            UpdateProyekView(
                proyekId = proyekId,
                canNavigateBack = true,
                navigateUp = { navController.navigateUp() }
            )
        }

        // Rute untuk DetailProyekView
        composable(
            route = "${DestinasiDetailProyek.route}/{proyekId}",
            arguments = listOf(navArgument("proyekId") { type = NavType.IntType })
        ) { backStackEntry ->
            val proyekId = backStackEntry.arguments?.getInt("proyekId") ?: 0
            DetailProyekView(
                navigateBack = { navController.navigateUp() }, // Navigasi kembali
                navigateToEdit = { "${DestinasiUpdateProyek.route}/{proyekId}" },
                modifier = Modifier
            )
        }

        // Rute untuk DetailTugasView
        composable(
            route = "${DestinasiDetailTugas.route}/{tugasId}",
            arguments = listOf(navArgument("tugasId") { type = NavType.IntType })
        ) { backStackEntry ->
            val tugasId = backStackEntry.arguments?.getInt("tugasId") ?: 0
            DetailTugasView(
                tugasId = tugasId,
                canNavigateBack = true,
                navigateUp = { navController.navigateUp() },
                onDeleteTugas = { /* Handle delete tugas */ },
                onUpdateTugas = { /* Handle update tugas */ }
            )
        }

        // Rute untuk TambahTugasView
        composable(route = DestinasiTambahTugas.route) {
            TambahTugasView(
                canNavigateBack = true,
                navigateUp = { navController.navigateUp() }
            )
        }

        // Rute untuk UpdateTugasView
        composable(
            route = "${DestinasiUpdateTugas.route}/{tugasId}",
            arguments = listOf(navArgument("tugasId") { type = NavType.IntType })
        ) { backStackEntry ->
            val tugasId = backStackEntry.arguments?.getInt("tugasId") ?: 0
            UpdateTugasView(
                tugasId = tugasId,
                canNavigateBack = true,
                navigateUp = { navController.navigateUp() }
            )
        }
    }
}