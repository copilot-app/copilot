package com.copilot.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.copilot.data.model.Entry

class MapViewModel : ViewModel() {
    private val dummyData = listOf(
        Entry("Entry 1", "Description 1"),
        Entry("Entry 2", "Description 2"),
    )

    private val _entryList = MutableLiveData<List<Entry>>(dummyData)
    val entryList: LiveData<List<Entry>> = _entryList
}