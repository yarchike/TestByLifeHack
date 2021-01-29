package com.martynov.testbylifehack

import android.app.Application
import com.martynov.testbylifehack.api.API
import com.martynov.testbylifehack.repository.NetworkRepository
import com.martynov.testbylifehack.repository.Repository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App: Application() {
    companion object {
        lateinit var repository: Repository
            private set
    }

    override fun onCreate() {
        super.onCreate()
        val retrofit = Retrofit.Builder()
            .baseUrl("$BASE_URL/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(API::class.java)
        repository = NetworkRepository(api)
    }
}