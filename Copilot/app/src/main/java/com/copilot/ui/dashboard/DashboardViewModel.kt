package com.copilot.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.copilot.MainActivity
import com.copilot.data.model.Entry
import com.google.android.gms.maps.model.LatLng

class DashboardViewModel : ViewModel() {

    private val _speed = MutableLiveData<Float>(50F)
    val speed: LiveData<Float> = _speed

    private val _fuel = MutableLiveData<Float>(75.0F)
    val fuel: LiveData<Float> = _fuel

    private val _location = MutableLiveData<LatLng>(LatLng(51.131120, 17.005997))
    val location: LiveData<LatLng> = _location

    fun updateLocation() {
        _location.value = MainActivity.location
    }
}