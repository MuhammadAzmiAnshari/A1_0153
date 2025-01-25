package com.example.projectmanagement.applications

import android.app.Application
import com.example.myapp.network.AppContainer
import com.example.myapp.network.RetrofitContainer

class ProjectApplications : Application() {

    // Properti untuk menyimpan instance AppContainer
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        // Inisialisasi container saat aplikasi dimulai
        container = RetrofitContainer()
    }
}