package ru.practicum.android.diploma.filter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.presentation.model.FiltrationState

class FiltrationViewModel : ViewModel() {
    var lastText: String = ""
    var workPlace: String =""
    var industry: String = ""
    var salary: String = ""
    var checkbox = false
    private val stateFiltersLiveData = MutableLiveData<Pair<Boolean, FiltrationState>>()
    private val stateCheckBoxLiveData = MutableLiveData<Boolean>()
    fun observeFiltersState(): LiveData<Pair<Boolean, FiltrationState>> = stateFiltersLiveData
    fun observeCheckBoxState(): LiveData<Boolean> = stateCheckBoxLiveData
    fun checkboxClick() {
        checkbox = if (checkbox) false else true
        stateCheckBoxLiveData.postValue(checkbox)
    }
}
