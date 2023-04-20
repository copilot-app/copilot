package com.copilot.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.copilot.data.model.Entry
import com.copilot.data.model.Location
import com.google.android.gms.maps.model.LatLng
import java.time.Instant

class MapViewModel : ViewModel() {
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
            Location(LatLng(51.099619, 17.035227), Instant.parse("2023-04-20T10:00:00Z")),
            Location(LatLng(51.099237, 17.035385), Instant.parse("2023-04-20T10:05:00Z")),
            Location(LatLng(51.098766, 17.035438), Instant.parse("2023-04-20T10:10:00Z")),
            Location(LatLng(51.098540, 17.035492), Instant.parse("2023-04-20T10:15:00Z")),
            Location(LatLng(51.098315, 17.035557), Instant.parse("2023-04-20T10:20:00Z")),
            Location(LatLng(51.098056, 17.035635), Instant.parse("2023-04-20T10:25:00Z")),
            Location(LatLng(51.097819, 17.035698), Instant.parse("2023-04-20T10:30:00Z")),
            Location(LatLng(51.097425, 17.035807), Instant.parse("2023-04-20T10:35:00Z")),
            Location(LatLng(51.097213, 17.035875), Instant.parse("2023-04-20T10:40:00Z")),
            Location(LatLng(51.096927, 17.035960), Instant.parse("2023-04-20T10:45:00Z"))
        )
    }

    private val _entryList = MutableLiveData<List<Entry>>(DummyData.entryList)
    val entryList: LiveData<List<Entry>> = _entryList

    private val _locationHistory = MutableLiveData<List<Location>>(DummyData.locationHistory)
    val locationHistory: LiveData<List<Location>> = _locationHistory

    fun deleteEntry(entry: Entry?) {
        _entryList.value = _entryList.value?.filter { it != entry }
    }
}