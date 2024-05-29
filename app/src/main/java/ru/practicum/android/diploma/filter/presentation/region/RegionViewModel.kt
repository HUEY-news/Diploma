package ru.practicum.android.diploma.filter.presentation.region

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.api.FiltrationInteractor
import ru.practicum.android.diploma.filter.domain.api.SearchAreasInteractor
import ru.practicum.android.diploma.filter.domain.model.Area
import ru.practicum.android.diploma.filter.domain.model.Country
import ru.practicum.android.diploma.filter.presentation.region.model.RegionState

class RegionViewModel(
    private val searchAreasInteractor: SearchAreasInteractor,
    private val filtrationInteractor: FiltrationInteractor,
) : ViewModel() {
    private val stateLiveData = MutableLiveData<RegionState>()
    private var parentId: String = ""
    private var countryList = mutableListOf<Country>()

    fun observeState(): LiveData<RegionState> = stateLiveData
    fun searchRequest() {
        renderState(RegionState.Loading)
        val countryName = filtrationInteractor.getFilter()?.countryName
        if (countryName != null) {
            searchRegionsCountry(countryName)
        } else {
            searchAllRegions()
        }
    }

    private fun searchRegionsCountry(countryName: String) {
        viewModelScope.launch {
            searchAreasInteractor
                .searchRegionsByCountry(countryName)
                .collect { pair ->
                    bindState(pair)
                }
        }
    }

    private fun bindState(pair: Pair<List<Area>?, String?>) {
        val regions = ArrayList<Area>()
        if (pair.first != null) {
            regions.addAll(pair.first!!)
            renderState(
                RegionState.Content(
                    regions = regions
                )
            )
        }
        when {
            pair.second != null -> {
                renderState(
                    RegionState.Error(
                        errorMessage = pair.second!!
                    ),
                )
            }
        }
    }

    private fun searchAllRegions() {
        viewModelScope.launch {
            searchAreasInteractor
                .searchAreas()
                .collect { pair ->
                    bindStateAllRegions(pair)
                }
        }
    }

    private fun bindStateAllRegions(pair: Pair<List<Country>?, String?>) {
        if (pair.first != null) {
            createContent(pair.first!!)
        }
        when {
            pair.second != null -> {
                renderState(
                    RegionState.Error(
                        errorMessage = pair.second!!
                    ),
                )
            }
        }
    }

    private fun createContent(first: List<Country>) {
        countryList.addAll(first)
        val regions = ArrayList<Area>()
        countryList.forEach { country ->
            country.areas?.forEach { area ->
                area?.let { regions.add(it) }
            }
        }
        renderState(
            RegionState.Content(
                regions = regions
            )
        )
    }

    fun getRegionCountry(): String {
        var stringRegionCountry = ""
        countryList.forEach { country ->
            if (country.id.equals(parentId)) {
                stringRegionCountry = country.name!!
            }
        }
        return stringRegionCountry
    }

    fun setCountryFilter(area: Area) {
        filtrationInteractor.updateArea(area)
    }

    private fun renderState(state: RegionState) {
        stateLiveData.postValue(state)
    }

    fun setParentId(parentId: String?) {
        if (parentId != null) {
            this.parentId = parentId
        }
    }
}
