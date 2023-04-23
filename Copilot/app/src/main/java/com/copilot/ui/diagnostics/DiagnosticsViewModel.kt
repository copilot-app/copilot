package com.copilot.ui.diagnostics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.copilot.data.model.ErrorMessage
import com.copilot.data.model.InformationMessage

class DiagnosticsViewModel : ViewModel() {
    private val dummyDataForInformation = listOf(
        InformationMessage("Water temperature", "90"),
        InformationMessage("Oil temperature", "60"),
        InformationMessage("RPM", "900"),
        InformationMessage("Fuel consumption", "5l / 100km"))

    private val dummyDataForErrors = listOf(
        ErrorMessage("Fuel Delivery Error", "P0148"),
        ErrorMessage("Clutch Position Control Error", "P0810"),
        ErrorMessage("Exhaust Pressure Sensor Low", "P0472"),
        ErrorMessage("Control Module Programming Error", "P0602"))

    private val _errorsList = MutableLiveData<List<ErrorMessage>>(dummyDataForErrors)
    val errorsList: LiveData<List<ErrorMessage>> = _errorsList

    private val _informationList = MutableLiveData<List<InformationMessage>>(dummyDataForInformation)
    val informationList: LiveData<List<InformationMessage>> = _informationList

}
