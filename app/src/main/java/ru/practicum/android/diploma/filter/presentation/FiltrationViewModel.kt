package ru.practicum.android.diploma.filter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.presentation.model.FiltrationState

class FiltrationViewModel : ViewModel() {
    var lastText: String = ""
    var workPlace: String = ""
    var industry: String = ""
    var salary: String = ""
    var checkbox = false
    private val stateLiveData = MutableLiveData<Pair<Boolean, FiltrationState>>()
    fun observeState(): LiveData<Pair<Boolean, FiltrationState>> = stateLiveData
    fun checkboxClick(){
        checkbox= if (checkbox) false else true
        stateLiveData.postValue(Pair(true, FiltrationState.FiltersContent(workPlace,industry,salary,checkbox)))
    }
}
