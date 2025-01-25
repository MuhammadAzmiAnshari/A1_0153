package com.example.projectmanagement.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.projectmanagement.applications.ProjectApplications
import com.example.projectmanagement.ui.viewmodel.proyek.HomeProyekViewModel
import com.example.projectmanagement.ui.viewmodel.proyek.TambahProyekViewModel
import com.example.projectmanagement.ui.viewmodel.proyek.UpdateProyekViewModel
import com.example.projectmanagement.ui.viewmodel.tim.HomeTimViewModel
import com.example.projectmanagement.ui.viewmodel.tim.TambahTimViewModel
import com.example.projectmanagement.ui.viewmodel.tim.UpdateTimViewModel
import com.example.projectmanagement.ui.viewmodel.tugas.DetailTugasViewModel
import com.example.projectmanagement.ui.viewmodel.tugas.TambahTugasViewModel
import com.example.projectmanagement.ui.viewmodel.tugas.UpdateTugasViewModel
import com.example.projectmanagement.viewmodel.DetailProyekViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        //////////////////////////////////////////////////////////////////////////////////////////// Proyek
        // HomeProyekViewModel
        initializer {
            HomeProyekViewModel(ProjectApplication().container.proyekRepository)
        }
        // TambahProyekViewModel
        initializer {
            TambahProyekViewModel(ProjectApplication().container.proyekRepository)
        }
        // UpdateProyekViewModel
        initializer {
            UpdateProyekViewModel(
                proyekRepository = ProjectApplication().container.proyekRepository,
                savedStateHandle = createSavedStateHandle(),

            )
        }
        // DetailProyekViewModel
        initializer {
            DetailProyekViewModel(proyekRepository = ProjectApplication().container.proyekRepository,
                savedStateHandle = createSavedStateHandle()
            )
        }

        //////////////////////////////////////////////////////////////////////////////////////////// Tugas
        // HomeTugasViewModel
        initializer {
            DetailTugasViewModel(
                tugasRepository = ProjectApplication().container.tugasRepository
            )
        }
        // TambahTugasViewModel
        initializer {
            TambahTugasViewModel(
                tugasRepository = ProjectApplication().container.tugasRepository
            )
        }
        // UpdateTugasViewModel
        initializer {
            UpdateTugasViewModel(
                tugasRepository = ProjectApplication().container.tugasRepository,
                tugasId = 0 // Default value, akan diganti saat digunakan
            )
        }

        //////////////////////////////////////////////////////////////////////////////////////////// Tim
        initializer {
            HomeTimViewModel(ProjectApplication().container.timRepository)
        }
        // TambahTimViewModel
        initializer {
            TambahTimViewModel(ProjectApplication().container.timRepository)
        }
        // UpdateTimViewModel
        initializer {
            UpdateTimViewModel(
                timRepository = ProjectApplication().container.timRepository,
                savedStateHandle = createSavedStateHandle(),

                )
        }

    }
}

// Extension function untuk mendapatkan instance ProjectApplications dari CreationExtras
fun CreationExtras.ProjectApplication(): ProjectApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ProjectApplications)