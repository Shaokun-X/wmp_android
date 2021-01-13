package com.faircorp.service

import com.faircorp.Constants
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RemoteWindowApiService {
    val windowApiService : WindowApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(Constants.API_BASE_URL)
            .build()
            .create(WindowApiService::class.java)
    }

}