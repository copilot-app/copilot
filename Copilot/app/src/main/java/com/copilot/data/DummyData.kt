package com.copilot.data

import com.copilot.data.model.Entry
import com.copilot.data.model.Location
import com.google.android.gms.maps.model.LatLng
import java.time.Instant

object DummyData {
    val entryList = listOf(
        Entry("Entry 1", "Description"),
        Entry(
            "Entry 2",
            "Long Description Lorem Ipsum Dolor Sit Amet Consectetur Adipiscing Elit"
        ),
        Entry("Entry 3", "Description"),
        Entry(
            "Entry 4",
            "Even Longer Description Lorem Ipsum Dolor Sit Amet Consectetur Adipiscing Elit Lorem Ipsum Dolor Sit Amet Consectetur Adipiscing Elit"
        ),
    )
    val locationHistory = listOf(
        Location(LatLng(51.107858, 17.037076), Instant.parse("2023-04-20T10:00:00Z"), 100.0),
        Location(LatLng(51.107381, 17.035978), Instant.parse("2023-04-20T10:05:00Z"), 95.0),
        Location(LatLng(51.107229, 17.033839), Instant.parse("2023-04-20T10:10:00Z"), 90.0),
        Location(LatLng(51.107524, 17.031866), Instant.parse("2023-04-20T10:15:00Z"), 70.0),
        Location(LatLng(51.108048, 17.029651), Instant.parse("2023-04-20T10:20:00Z"), 65.0),
        Location(LatLng(51.108572, 17.029704), Instant.parse("2023-04-20T10:25:00Z"), 40.0),
        Location(LatLng(51.109077, 17.029583), Instant.parse("2023-04-20T10:30:00Z"), 35.0),
        Location(LatLng(51.109315, 17.028832), Instant.parse("2023-04-20T10:35:00Z"), 30.0),
        Location(LatLng(51.110082, 17.029082), Instant.parse("2023-04-20T10:40:00Z"), 20.0),
        Location(LatLng(51.111254, 17.029606), Instant.parse("2023-04-20T10:45:00Z"), 15.0)
    )
}