package com.example.dwitchapp.api

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Date
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter


object ApiClient {
    private val moshi = Moshi.Builder().add(Date::class.java, Rfc3339DateJsonAdapter()).build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dwitch.pickle-forge.app/api/") // Remplacez par l'URL de votre API
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val dwitchService: OrderApiService = retrofit.create(OrderApiService::class.java)}
