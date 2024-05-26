package ru.practicum.android.diploma.filter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.domain.api.SaveFiltersInteractor
import ru.practicum.android.diploma.filter.domain.model.FiltersSave

class FiltrationViewModel(private val saveFiltersInteractor: SaveFiltersInteractor) : ViewModel() {
    var lastText: String = ""
    var workPlace: String = "Место работы"
    var industry: String = "Отрасль"
    var salary: String = ""
    var checkbox = false
    var checkboxChange = false
    private var filters = FiltersSave("Место работы","Отрасль","",false )
    private val stateFiltersLiveData = MutableLiveData<FiltersSave>()
    private val stateCheckBoxLiveData = MutableLiveData<Boolean>()

    init {
        getFilter()
    }
    fun observeFiltersState(): LiveData<FiltersSave> = stateFiltersLiveData
    fun observeCheckBoxState(): LiveData<Boolean> = stateCheckBoxLiveData
    fun checkboxClick() {
        checkbox = if (checkbox) false else true
        checkboxChange = if (checkboxChange) false else true
        stateCheckBoxLiveData.postValue(checkbox)
    }
    fun saveFilters(filters: FiltersSave){
        saveFiltersInteractor.saveFilters(filters)
    }

    fun getFilter(){
        filters = saveFiltersInteractor.getFilters()
        lastText= filters.salary!!
        stateCheckBoxLiveData.postValue(filters.checkbox)
        stateFiltersLiveData.postValue(filters)
    }
}
