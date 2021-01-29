package com.martynov.testbylifehack

import java.util.regex.Pattern


fun isValidUrl(url: String) =
    Pattern.compile("^(https?:\\/\\/)?([\\w-]{1,32}\\.[\\w-]{1,32})[^\\s@]*\$").matcher(url)
        .matches()

fun isValidPhoneNumber(number: String) =
    Pattern.compile("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}\$").matcher(number)
        .matches()

