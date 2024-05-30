package ru.practicum.android.diploma.filter.presentation.model

sealed interface FiltrationState {

    object EmptyFilters : FiltrationState

    object EmptyFilterIndustry : FiltrationState
    data class FilterIndustryState(
        val industryName: String,
    ) : FiltrationState

    object EmptyFilterSalary : FiltrationState
    data class FilterSalaryState(
        val salary: String,
    ) : FiltrationState

    object EmptyFilterCheckBox : FiltrationState
    data class CheckBoxState(
        val isCheck: Boolean,
    ) : FiltrationState
}
