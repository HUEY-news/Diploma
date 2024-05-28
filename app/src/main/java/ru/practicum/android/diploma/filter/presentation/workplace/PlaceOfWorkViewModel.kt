package ru.practicum.android.diploma.filter.presentation.workplace

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.presentation.workplace.model.AreaState

class PlaceOfWorkViewModel : ViewModel() {
    private val stateLiveData = MutableLiveData<AreaState>()
    fun observeState(): LiveData<AreaState> = stateLiveData

    fun setState(state: AreaState) {
        stateLiveData.value = state
    }
}
