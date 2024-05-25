package ru.practicum.android.diploma.filter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.presentation.model.FiltrationState

class FiltrationViewModel : ViewModel(
) {
    private val stateLiveData = MutableLiveData<Pair<Boolean, FiltrationState>>()
    fun observeState(): LiveData<Pair<Boolean, FiltrationState>> = stateLiveData
}
