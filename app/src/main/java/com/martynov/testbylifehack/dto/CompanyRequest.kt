package com.martynov.testbylifehack.dto

import com.martynov.testbylifehack.BASE_URL

data class CompanyRequest(val id: Int,
                     val name: String,
                     val img: String
                     ) {
    val url
        get() = "$BASE_URL/$img"

}