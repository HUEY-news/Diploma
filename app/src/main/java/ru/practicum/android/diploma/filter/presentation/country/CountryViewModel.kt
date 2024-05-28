package ru.practicum.android.diploma.filter.presentation.country

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.api.SearchAreasInteractor
import ru.practicum.android.diploma.filter.domain.model.Country
import ru.practicum.android.diploma.filter.presentation.country.model.CountryState

class CountryViewModel(
    private val searchAreasInteractor: SearchAreasInteractor,
) : ViewModel() {
    private val stateLiveData = MutableLiveData<CountryState>()
    fun observeState(): LiveData<CountryState> = stateLiveData
    fun searchRequest() {
        renderState(CountryState.Loading)
        viewModelScope.launch {
            searchAreasInteractor
                .searchAreas()
                .collect { pair ->
                    val countries = ArrayList<Country>()
                    if (pair.first != null) {
                        countries.addAll(pair.first!!)
                        renderState(
                            CountryState.Content(
                                countries = countries
                            )
                        )
                    }
                    when {
                        pair.second != null -> {
                            renderState(
                                CountryState.Error(
                                    errorMessage = pair.second!!
                                ),
                            )
                        }
                    }
                }
        }
    }

    private fun renderState(state: CountryState) {
        stateLiveData.postValue(state)
    }
}
