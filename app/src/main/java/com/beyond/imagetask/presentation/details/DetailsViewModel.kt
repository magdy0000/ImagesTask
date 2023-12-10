package com.beyond.imagetask.presentation.details

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beyond.imagetask.domian.enums.FilterType
import com.beyond.imagetask.domian.usecases.filter.IFilterUseCase
import com.beyond.imagetask.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val filterUseCase: IFilterUseCase,
) : BaseViewModel() {

    private val _filteredImageLiveData = MutableLiveData<Bitmap>()
    val filteredImageLiveData get() = _filteredImageLiveData

    fun applyFilter(image: Bitmap, filterType: FilterType) {
        viewModelScope.launch {
            val resultImage = filterUseCase.execute(image, filterType)
            _filteredImageLiveData.postValue(resultImage)
        }
    }


}