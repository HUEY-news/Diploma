package ru.practicum.android.diploma.filter.presentation.region

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.api.SearchAreasInteractor
import ru.practicum.android.diploma.filter.domain.model.Area
import ru.practicum.android.diploma.filter.presentation.region.model.RegionState

class RegionViewModel(private val searchAreasInteractor: SearchAreasInteractor) : ViewModel() {
    private val stateLiveData = MutableLiveData<RegionState>()
    fun observeState(): LiveData<RegionState> = stateLiveData
    fun searchRequest() {
        renderState(RegionState.Loading)
        viewModelScope.launch {
            searchAreasInteractor
                .searchAllRegions()
                .collect { pair ->
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
        }
    }

    private fun renderState(state: RegionState) {
        stateLiveData.postValue(state)
    }
}
