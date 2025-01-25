package com.example.projectmanagement.ui.navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projectmanagement.ui.view.HomeView
import com.example.projectmanagement.ui.view.proyek.DetailProyekView
import com.example.projectmanagement.ui.view.proyek.HomeProyekView
import com.example.projectmanagement.ui.view.proyek.TambahProyekView
import com.example.projectmanagement.ui.view.proyek.UpdateProyekView
import com.example.projectmanagement.ui.view.tim.DestinasiDetailTim
import com.example.projectmanagement.ui.view.tim.DetailTimView
import com.example.projectmanagement.ui.view.tim.HomeTimView
import com.example.projectmanagement.ui.view.tim.TambahTimView
import com.example.projectmanagement.ui.view.tim.UpdateTimView
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
        startDestination = DestinasiHome.route,
        modifier = modifier
    ) {

        // Rute untuk HomeView
        composable(route = DestinasiHome.route) {
            HomeView(
                onProyekButton = { navController.navigate(DestinasiHomeProyek.route) },
                onTimButton = { navController.navigate(DestinasiHomeTim.route) }
            )
        }
//////////////////////////////////////////////////////////////////////////////////////////////////// Proyek
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
            route = DestinasiUpdateProyek.routeWithArg,
            arguments = listOf(navArgument(DestinasiUpdateProyek.PROYEK_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            val proyekId = backStackEntry.arguments?.getInt(DestinasiUpdateProyek.PROYEK_ID) ?: 0
            UpdateProyekView(
                proyekId = proyekId,
                canNavigateBack = true,
                navigateUp = { navController.navigateUp() }
            )
        }

        // Rute untuk DetailProyekView
        composable(
            route = DestinasiDetailProyek.routeWithArg,
            arguments = listOf(navArgument(DestinasiDetailProyek.PROYEK_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            val proyekId = backStackEntry.arguments?.getInt(DestinasiDetailProyek.PROYEK_ID) ?: 0
            DetailProyekView(
                navController = navController, // Tambahkan ini
                navigateBack = { navController.navigateUp() },
                navigateToEdit = { proyekId ->
                    navController.navigate("${DestinasiUpdateProyek.route}/$proyekId")
                },
                modifier = Modifier
            )
        }
//////////////////////////////////////////////////////////////////////////////////////////////////// Tim
        // Rute untuk HomeTimView
        composable(route = DestinasiHomeTim.route) {
            HomeTimView(
                onTambahTimClick = { navController.navigate(DestinasiTambahTim.route) },
                onDetailTimClick = { timId ->
                    navController.navigate("${DestinasiDetailTim.route}/$timId") // Navigasi ke DetailTimView
                },
                canNavigateBack = true,
                navigateUp = { navController.navigateUp() }
            )
        }

        // Rute untuk TambahTimView
        composable(route = DestinasiTambahTim.route) {
            TambahTimView(
                canNavigateBack = true,
                navigateUp = { navController.navigateUp() }
            )
        }

        // Rute untuk UpdateTimView
        composable(
            route = DestinasiUpdateTim.routeWithArg,
            arguments = listOf(navArgument(DestinasiUpdateTim.TIM_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            val timId = backStackEntry.arguments?.getInt(DestinasiUpdateTim.TIM_ID) ?: 0
            UpdateTimView(
                timId = timId,
                canNavigateBack = true,
                navigateUp = { navController.navigateUp() }
            )
        }
        // Rute untuk DetailTimView
        composable(
            route = DestinasiDetailTim.routeWithArg,
            arguments = listOf(navArgument(DestinasiDetailTim.TIM_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            val timId = backStackEntry.arguments?.getInt(DestinasiDetailTim.TIM_ID) ?: 0
            DetailTimView(
                navigateBack = { navController.navigateUp() },
                navigateToEdit = { timId ->
                    navController.navigate("${DestinasiUpdateTim.route}/$timId")
                }
            )
        }

//////////////////////////////////////////////////////////////////////////////////////////////////// Tugas
        // Rute untuk DetailTugasView
        composable(
            route = DestinasiDetailTugas.routeWithArg,
            arguments = listOf(navArgument(DestinasiDetailTugas.TUGAS_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            val tugasId = backStackEntry.arguments?.getInt(DestinasiDetailTugas.TUGAS_ID) ?: 0
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
            route = DestinasiUpdateTugas.routeWithArg,
            arguments = listOf(navArgument(DestinasiUpdateTugas.TUGAS_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            val tugasId = backStackEntry.arguments?.getInt(DestinasiUpdateTugas.TUGAS_ID) ?: 0
            UpdateTugasView(
                tugasId = tugasId,
                canNavigateBack = true,
                navigateUp = { navController.navigateUp() }
            )
        }
    }
}