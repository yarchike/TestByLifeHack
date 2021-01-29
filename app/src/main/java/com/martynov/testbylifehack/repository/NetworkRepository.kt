package com.martynov.testbylifehack.repository

import com.martynov.testbylifehack.api.API
import com.martynov.testbylifehack.dto.CompanyRequest
import com.martynov.testbylifehack.model.CompanyModel
import retrofit2.Response

class NetworkRepository(private val api: API): Repository {
    override suspend fun getCompanyList(): Response<List<CompanyRequest>> =
        api.getCompanyList()

    override suspend fun getCompayById(id: Int): Response<List<CompanyModel>>  = api.getCompanyById(id)

}