package com.thequietz.travelog.schedule.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thequietz.travelog.schedule.model.PlaceModel
import com.thequietz.travelog.schedule.model.PlaceSelected
import com.thequietz.travelog.schedule.repository.SchedulePlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchedulePlaceViewModel @Inject constructor(
    private val repository: SchedulePlaceRepository
) : ViewModel() {
    private var _placeList = MutableLiveData<List<PlaceModel>>()
    val placeList: LiveData<List<PlaceModel>> = _placeList

    private var _placeSelectedList = MutableLiveData<MutableList<PlaceSelected>>()
    val placeSelectedList: LiveData<MutableList<PlaceSelected>> = _placeSelectedList

    fun loadPlaceList() {
        viewModelScope.launch {
            _placeList.value = repository.loadPlaceList()
        }
    }

    fun searchPlaceList(keyword: String) {
        viewModelScope.launch {
            _placeList.value = repository.searchPlaceList(keyword)
        }
    }

    fun initPlaceSelectedList() {
        _placeSelectedList.value = mutableListOf()
    }

    fun addPlaceSelectedList(index: Int, value: PlaceModel) {
        val isExisted = placeSelectedList.value?.find { it.value == value.cityName }
        if (isExisted != null) return

        val guideSelected = PlaceSelected(index, value.cityName)
        _placeSelectedList.value?.add(guideSelected)
        _placeSelectedList.value = _placeSelectedList.value
    }

    fun removePlaceSelectedList(position: Int) {
        val updated = _placeSelectedList.value?.filterIndexed { index, _ -> position != index }
        _placeSelectedList.value = updated?.toMutableList()
    }
}