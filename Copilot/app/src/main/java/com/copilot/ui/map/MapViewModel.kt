package com.copilot.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.copilot.data.model.Entry

class MapViewModel : ViewModel() {
    private val dummyData = listOf(
        Entry("Entry 1", "Description"),
        Entry("Entry 2", "Long Description Lorem Ipsum Dolor Sit Amet Consectetur Adipiscing Elit"),
        Entry("Entry 3", "Description"),
        Entry(
            "Entry 4",
            "Even Longer Description Lorem Ipsum Dolor Sit Amet Consectetur Adipiscing Elit Lorem Ipsum Dolor Sit Amet Consectetur Adipiscing Elit"
        ),
    )

    private val _entryList = MutableLiveData<List<Entry>>(dummyData)
    val entryList: LiveData<List<Entry>> = _entryList
}