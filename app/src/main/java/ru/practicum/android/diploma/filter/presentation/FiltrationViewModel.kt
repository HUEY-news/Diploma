package ru.practicum.android.diploma.filter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.domain.api.FiltrationInteractor
import ru.practicum.android.diploma.filter.presentation.model.FiltrationState
import ru.practicum.android.diploma.filter.presentation.workplace.model.AreaState

class FiltrationViewModel(
    private val filtrationInteractor: FiltrationInteractor,
) : ViewModel() {
    var lastText: String = ""
    var workPlace: String = "Место работы"
    var industry: String = "Отрасль"
    var checkbox = false
    var checkboxChange = false

    private val stateLiveDataFiltration = MutableLiveData<FiltrationState>()
    fun observeFiltrationState(): LiveData<FiltrationState> = stateLiveDataFiltration

    private val stateLiveDataWorkplace = MutableLiveData<AreaState>()
    fun observeWorkplaceState(): LiveData<AreaState> = stateLiveDataWorkplace
    fun updateWorkplaceFromShared() {
        val country = filtrationInteractor.getFilter()?.countryName
        val region = filtrationInteractor.getFilter()?.regionName
        if (country != null && region != null) {
            stateLiveDataWorkplace.postValue(AreaState.FullArea(country, region))
        } else if (country != null) {
            stateLiveDataWorkplace.postValue(AreaState.CountryName(country))
        } else {
            stateLiveDataWorkplace.postValue(AreaState.Empty)
        }
    }

    fun setSalary(inputTextFromApply: String) {
        stateLiveDataFiltration.postValue(FiltrationState.FilterSalaryState(inputTextFromApply))
    }

    fun setSalaryIsEmpty() {
        stateLiveDataFiltration.postValue(FiltrationState.EmptyFilterSalary)
    }

    fun checkboxClick() {
        checkbox = !checkbox
        stateLiveDataFiltration.postValue(FiltrationState.CheckBoxState(checkbox))
    }
}
