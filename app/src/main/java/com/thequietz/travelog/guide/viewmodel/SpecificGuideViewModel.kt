package com.thequietz.travelog.guide.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thequietz.travelog.data.GuideRepository
import com.thequietz.travelog.guide.Place
import com.thequietz.travelog.guide.view.SpecificGuideFragmentArgs
import com.thequietz.travelog.util.areaCodeList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SpecificGuideViewModel @Inject internal constructor(
    val guideRepository: GuideRepository
) : ViewModel() {
    companion object {
        var previousSearchCode = ""
    }

    private val _currentPlaceList = MutableLiveData<List<Place>>()
    val currentPlaceList: LiveData<List<Place>> = _currentPlaceList

    private val _currentSearch = MutableLiveData<String>()
    val currentSearch: LiveData<String> = _currentSearch

    private val _noData = MutableLiveData<Boolean>()
    val noData: LiveData<Boolean> = _noData

    fun initCurrentItem(args: SpecificGuideFragmentArgs) {
        viewModelScope.launch {
            val res = withContext(Dispatchers.IO) {
                guideRepository.loadDoSiByCode(args.item)
            }
            _currentPlaceList.value = res
            _currentSearch.value = areaCodeList.get(res.get(0).areaCode.toInt())
            previousSearchCode = res.get(0).areaCode
            _noData.value = currentPlaceList.value?.size == 0
        }
    }
}
