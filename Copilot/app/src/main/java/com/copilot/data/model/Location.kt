package com.copilot.data.model

import com.google.android.gms.maps.model.LatLng
import java.time.Instant

typealias KilometersPerHour = Double

data class Location(
    var coordinates: LatLng,
    var timestamp: Instant,
    var velocity: KilometersPerHour,
)