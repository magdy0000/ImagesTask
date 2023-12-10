package com.beyond.imagetask.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beyond.imagetask.domian.models.ImagesModel
import com.beyond.imagetask.domian.usecases.getimages.IImagesUseCase
import com.beyond.imagetask.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: IImagesUseCase) : BaseViewModel() {

    private val _imageLiveData = MutableLiveData<List<ImagesModel>>()
    val imageLiveData get() = _imageLiveData


    fun getImages(limit: Int = 20, hasBreeds: Boolean = true) {
        showLoading.value = true
        viewModelScope.launch(IO) {
            val images = useCase.getImages(limit, hasBreeds)
            if (images.isSuccess()) {
                _imageLiveData.postValue(images.getSuccessData())
            } else {
                showApiError.postValue(images.getErrorMessage())
            }
            showLoading.postValue(false)
        }
    }
}