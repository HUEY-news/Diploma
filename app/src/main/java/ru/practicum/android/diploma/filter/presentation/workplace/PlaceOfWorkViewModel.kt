package ru.practicum.android.diploma.filter.presentation.workplace

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.domain.api.FiltrationInteractor
import ru.practicum.android.diploma.filter.presentation.workplace.model.AreaState

class PlaceOfWorkViewModel(private val filtrationInteractor: FiltrationInteractor) : ViewModel() {
    private val stateLiveData = MutableLiveData<AreaState>()
    fun observeState(): LiveData<AreaState> = stateLiveData
    private var countryName: String? = null

    fun updateInfoFromShared() {
        val country = filtrationInteractor.getFilter()?.countryName
        val region = filtrationInteractor.getFilter()?.regionName
        if ((country != null) && (region != null)) {
            stateLiveData.postValue(AreaState.FullArea(country, region))
        } else if (country != null) {
            if (!countryName.isNullOrEmpty()) {
                stateLiveData.postValue(AreaState.CountryName(countryName!!))
            }
            stateLiveData.postValue(AreaState.CountryName(country))
        } else if (region != null) {
            if (!countryName.isNullOrEmpty()) {
                stateLiveData.postValue(AreaState.FullArea(countryName!!, region))
            } else {
                stateLiveData.postValue(AreaState.RegionName(region))
            }
        } else {
            stateLiveData.postValue(AreaState.Empty)
        }
    }

    fun cleanCountryData() {
        filtrationInteractor.clearCountry()
        filtrationInteractor.clearArea()
        updateInfoFromShared()
    }

    fun cleanRegionData() {
        filtrationInteractor.clearArea()
        updateInfoFromShared()
    }

    fun setArgumentCountry(countryName: String) {
        this.countryName = countryName
    }
}
