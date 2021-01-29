package com.martynov.testbylifehack.repository

import com.martynov.testbylifehack.dto.CompanyRequest
import com.martynov.testbylifehack.model.CompanyModel
import retrofit2.Response

interface Repository {
    suspend fun getCompanyList(): Response<List<CompanyRequest>>
    suspend fun getCompayById(id: Int): Response<List<CompanyModel>>

}