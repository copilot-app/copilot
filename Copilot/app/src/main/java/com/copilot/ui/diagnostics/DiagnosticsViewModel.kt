package com.copilot.ui.diagnostics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DiagnosticsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Errors"
    }
    val text: LiveData<String> = _text
}