package com.faircorp.service

import com.faircorp.Constants
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RemoteRoomApiService {
    val roomApiService : RoomApiService by lazy {
        Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(Constants.API_BASE_URL)
                .build()
                .create(RoomApiService::class.java)
    }
}