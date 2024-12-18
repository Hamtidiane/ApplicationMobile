package com.example.dwitchapp.api

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory



object ApiClient {
    private val moshi = Moshi.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dwitch.pickle-forge.app/api") // Remplacez par l'URL de votre API
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    fun <T> create(service: Class<T>): T = retrofit.create(service)
}
