package com.martynov.testbylifehack.api

import com.martynov.testbylifehack.dto.CompanyRequest
import com.martynov.testbylifehack.model.CompanyModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("test.php")
    suspend fun getCompanyList(): Response<List<CompanyRequest>>

    @GET("test.php")
    suspend fun getCompanyById(@Query("id") id: Int): Response<List<CompanyModel>>

}
