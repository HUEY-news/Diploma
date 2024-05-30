package ru.practicum.android.diploma.filter.presentation.industry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.details.presentation.model.StateLoadVacancy
import ru.practicum.android.diploma.filter.domain.api.FiltrationInteractor
import ru.practicum.android.diploma.filter.domain.api.SearchIndustriesInteractor
import ru.practicum.android.diploma.filter.domain.model.Industry
import ru.practicum.android.diploma.filter.presentation.industry.model.IndustriesState
import ru.practicum.android.diploma.filter.presentation.industry.model.IndustryState

class IndustryViewModel(
    private val searchIndustriesInteractor: SearchIndustriesInteractor,
    private val filtrationInteractor: FiltrationInteractor,
) : ViewModel() {
    private val stateLiveData = MutableLiveData<IndustriesState>()
    private val stateLiveDataIndustry = MutableLiveData<IndustryState>()
    fun observeState(): LiveData<IndustriesState> = stateLiveData
    fun observeStateIndustry(): LiveData<IndustryState> = stateLiveDataIndustry
    fun searchRequest() {
        viewModelScope.launch {
            StateLoadVacancy.Loading
            searchIndustriesInteractor
                .searchIndustries()
                .collect { pair ->
                    val industries = ArrayList<Industry>()
                    if (pair.first != null) {
                        industries.addAll(pair.first!!)
                        renderState(
                            IndustriesState.Content(
                                industries = industries
                            )
                        )
                    }
                    when {
                        pair.second != null -> {
                            renderState(
                                IndustriesState.Error(
                                    errorMessage = pair.second!!
                                ),
                            )
                        }
                    }
                }
        }
    }

    private fun renderState(state: IndustriesState) {
        stateLiveData.postValue(state)
    }

    fun saveIndustryFromAdapter(industry: Industry) {
        stateLiveDataIndustry.postValue(IndustryState.ContentIndustry(industry))
    }

    fun saveIndustry(industry: Industry) {
        filtrationInteractor.updateIndustry(industry = industry)
    }
}
