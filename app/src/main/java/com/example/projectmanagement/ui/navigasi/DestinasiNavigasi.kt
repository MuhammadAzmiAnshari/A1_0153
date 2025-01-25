package com.example.projectmanagement.ui.navigasi

interface DestinasiNavigasi {
    val route: String
    val titleRes: String
}

//////////////////////////////////////////////////////////////////////////////////////////////////// Destinasi Proyek
object DestinasiHomeProyek: DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Daftar Proyek"
}

object DestinasiTambahProyek: DestinasiNavigasi {
    override val route = "tambah_proyek"
    override val titleRes = "Tambah Proyek"
}

object DestinasiUpdateProyek: DestinasiNavigasi {
    override val route = "update_proyek/{proyekId}"
    override val titleRes = "Update Proyek"
}

object DestinasiDetailProyek : DestinasiNavigasi {
    override val route = "detail_proyek"
    const val PROYEK_ID = "proyekId"
    override val titleRes = "Detail Proyek"
    val routeWithArg = "$route/{$PROYEK_ID}"
}

//////////////////////////////////////////////////////////////////////////////////////////////////// Destinasi Tim
object DestinasiHomeTim : DestinasiNavigasi {
    override val route = "home_tim"
    override val titleRes = "Daftar Tim"
}

object DestinasiTambahTim : DestinasiNavigasi {
    override val route = "tambah_tim"
    override val titleRes = "Tambah Tim"
}

object DestinasiUpdateTim : DestinasiNavigasi {
    override val route = "update_tim/{timId}"
    override val titleRes = "Update Tim"
}
//////////////////////////////////////////////////////////////////////////////////////////////////// Destinasi Tugas

object DestinasiDetailTugas : DestinasiNavigasi {
    override val route = "detail_tugas/{tugasId}"
    override val titleRes = "Detail Tugas"
}

object DestinasiTambahTugas : DestinasiNavigasi {
    override val route = "tambah_tugas"
    override val titleRes = "Tambah Tugas"
}

object DestinasiUpdateTugas : DestinasiNavigasi {
    override val route = "update_tugas/{tugasId}"
    override val titleRes = "Update Tugas"
}