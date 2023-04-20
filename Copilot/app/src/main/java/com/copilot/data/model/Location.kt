package com.copilot.data.model

import com.google.android.gms.maps.model.LatLng
import java.time.Instant

data class Location(
    var coordinates: LatLng,
    var timestamp: Instant
)