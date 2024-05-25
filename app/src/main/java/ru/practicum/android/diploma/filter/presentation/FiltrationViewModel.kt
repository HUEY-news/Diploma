package ru.practicum.android.diploma.filter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.presentation.model.FiltrationState

class FiltrationViewModel : ViewModel() {
    var lastText: String = ""
    var workPlace: String = "Место работы"
    var industry: String = "Отрасль"
    var salary: String = ""
    var checkbox = false
    var checkboxChange = false
    private val stateFiltersLiveData = MutableLiveData<FiltrationState>()
    private val stateCheckBoxLiveData = MutableLiveData<Boolean>()
    fun observeFiltersState(): LiveData<FiltrationState> = stateFiltersLiveData
    fun observeCheckBoxState(): LiveData<Boolean> = stateCheckBoxLiveData
    fun checkboxClick() {
        checkbox = if (checkbox) false else true
        checkboxChange = if (checkboxChange) false else true
        stateCheckBoxLiveData.postValue(checkbox)
    }
}
