package com.copilot.data.model

import com.google.android.gms.maps.model.LatLng

data class Location(
    val coordinates: LatLng,
    val timestamp: Long,
)