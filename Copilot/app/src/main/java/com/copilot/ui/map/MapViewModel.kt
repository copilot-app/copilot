package com.copilot.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.copilot.data.DummyData
import com.copilot.data.model.Entry
import com.copilot.data.model.Location

class MapViewModel : ViewModel() {
    private val _entryList = MutableLiveData<List<Entry>>(DummyData.entryList)
    val entryList: LiveData<List<Entry>> = _entryList

    private val _locationHistory = MutableLiveData<List<Location>>(DummyData.locationHistory)
    val locationHistory: LiveData<List<Location>> = _locationHistory

    fun deleteEntry(entry: Entry?) {
        _entryList.value = _entryList.value?.filter { it != entry }
    }
}