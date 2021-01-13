package com.faircorp.model

data class RoomDto(
        val id: Long,
        val name: String,
        val floor: Long?,
        val currentTemperature: Double?,
        val targetTemperature: Double?,
        val heaterIds: List<Long>?,
        val windowIds: List<Long>?,
        val buildingId: Long?
)
