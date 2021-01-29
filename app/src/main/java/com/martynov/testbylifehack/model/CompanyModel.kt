package com.martynov.testbylifehack.model

import com.martynov.testbylifehack.BASE_URL

data class CompanyModel (
    val id: Int,
    val name: String,
    val img: String,
    val description: String,
    val lat: Double,
    val lon: Double,
    val www: String,
    val phone: String
        ){
    val url
        get() = "$BASE_URL/$img"
}
