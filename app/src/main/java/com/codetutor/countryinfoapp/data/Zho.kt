package com.codetutor.countryinfoapp.data

import kotlinx.serialization.Serializable

@Serializable
data class Zho(
    val common: String? = null,
    val official: String? = null
)