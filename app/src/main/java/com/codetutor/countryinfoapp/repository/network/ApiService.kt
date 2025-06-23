package com.codetutor.countryinfoapp.repository.network

import com.codetutor.countryinfoapp.data.Country
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("v3.1/all")
    suspend fun getAllCountries(): Response<MutableList<Country>>
}