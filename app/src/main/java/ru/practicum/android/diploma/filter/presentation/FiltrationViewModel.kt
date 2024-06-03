package ru.practicum.android.diploma.filter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.domain.api.FiltrationInteractor
import ru.practicum.android.diploma.filter.presentation.model.AreaState
import ru.practicum.android.diploma.filter.presentation.model.CheckBoxState
import ru.practicum.android.diploma.filter.presentation.model.FiltrationState
import ru.practicum.android.diploma.filter.presentation.model.IndustryState
import ru.practicum.android.diploma.filter.presentation.model.SalaryState

class FiltrationViewModel(
    private val filtrationInteractor: FiltrationInteractor
) : ViewModel() {

    private val stateLiveDataFiltration = MutableLiveData<FiltrationState>()
    private val stateLiveDataArea = MutableLiveData<AreaState>()
    private val stateLiveDataSalary = MutableLiveData<SalaryState>()
    private val stateLiveDataIndustry = MutableLiveData<IndustryState>()
    private val stateLiveDataCheckBox = MutableLiveData<CheckBoxState>()

    val salary by lazy(LazyThreadSafetyMode.NONE) {
        filtrationInteractor.getFilter()?.expectedSalary
    }

    fun observeFiltrationState(): LiveData<FiltrationState> = stateLiveDataFiltration
    fun observeAreaState(): LiveData<AreaState> = stateLiveDataArea
    fun observeIndustryState(): LiveData<IndustryState> = stateLiveDataIndustry
    fun observeCheckboxState(): LiveData<CheckBoxState> = stateLiveDataCheckBox

    private fun updateWorkplaceFromShared(country: String?, region: String?) {
        val stringCountryRegion: String
        if (country != null && region != null) {
            stringCountryRegion = "$country, $region"
            stateLiveDataArea.postValue(AreaState.WorkPlaceState(stringCountryRegion))
        } else if (country != null) {
            stringCountryRegion = "$country"
            stateLiveDataArea.postValue(AreaState.WorkPlaceState(stringCountryRegion))
        } else {
            stateLiveDataArea.postValue(AreaState.EmptyWorkplace)
        }
    }

    fun updateFilterParametersFromShared() {
        val industry = filtrationInteractor.getFilter()?.industryName
        val onlyWithSalary = filtrationInteractor.getFilter()?.isOnlyWithSalary
        val country = filtrationInteractor.getFilter()?.countryName
        val region = filtrationInteractor.getFilter()?.regionName

        updateWorkplaceFromShared(country, region)

        if (industry != null) setIndustry(industry)
        if (salary != null) setSalary(salary.toString())
        if (onlyWithSalary != null) {
            stateLiveDataCheckBox.postValue(CheckBoxState.IsCheck(onlyWithSalary))
        }
        if (checkOnNull(country, region, industry, salary, onlyWithSalary)) {
            stateLiveDataFiltration.postValue(FiltrationState.EmptyFilters)
        }
    }

    private fun checkOnNull(
        country: String?,
        region: String?,
        industry: String?,
        salary: Long?,
        onlyWithSalary: Boolean?,
    ): Boolean {
        return country.isNullOrEmpty() && region.isNullOrEmpty() &&
            industry.isNullOrEmpty() && checkSalary(salary, onlyWithSalary)
    }

    private fun checkSalary(salary: Long?, onlyWithSalary: Boolean?): Boolean {
        return salary == null && onlyWithSalary == null || onlyWithSalary == false
    }

    fun clearWorkplace() {
        filtrationInteractor.clearCountry()
        filtrationInteractor.clearArea()
        stateLiveDataArea.postValue(AreaState.EmptyWorkplace)
    }

    private fun setIndustry(industry: String) {
        stateLiveDataIndustry.postValue(IndustryState.FilterIndustryState(industry))
    }

    fun setIndustryIsEmpty() {
        filtrationInteractor.clearIndustry()
        stateLiveDataIndustry.postValue(IndustryState.EmptyFilterIndustry)
    }

    fun setSalary(inputTextFromApply: String) {
        filtrationInteractor.updateSalary(inputTextFromApply)
        stateLiveDataSalary.postValue(SalaryState.FilterSalaryState(inputTextFromApply))
    }

    fun setSalaryIsEmpty() {
        filtrationInteractor.clearSalary()
        stateLiveDataSalary.postValue(SalaryState.EmptyFilterSalary)
    }

    fun setCheckboxOnlyWithSalary(checkbox: Boolean) {
        filtrationInteractor.updateCheckBox(checkbox)
        stateLiveDataCheckBox.postValue(CheckBoxState.IsCheck(checkbox))
    }

    fun clearAllFilters() {
        clearWorkplace()
        setIndustryIsEmpty()
        setSalaryIsEmpty()
        setCheckboxOnlyWithSalary(false)
        stateLiveDataFiltration.postValue(FiltrationState.EmptyFilters)
    }

    fun setChangedState() {
        stateLiveDataFiltration.postValue(FiltrationState.ChangedFilter)
    }

    fun getIndustryFilterId(): String? {
        val filter = filtrationInteractor.getFilter()
        val industryId = filter?.industryId
        return industryId
    }
}
