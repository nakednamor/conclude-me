package com.nakednamor.conclude.me.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.nakednamor.conclude.me.data.weight.WeightDao
import com.nakednamor.conclude.me.data.weight.WeightRecord
import com.nakednamor.conclude.me.data.weight.WeightRepository


class LastWeightRecordsViewModel @ViewModelInject internal constructor(
    weightRepository: WeightRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val weightRecords: LiveData<List<WeightRecord>> = weightRepository.getLastWeightRecords()
}