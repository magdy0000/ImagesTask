package com.beyond.imagetask.presentation.base

import androidx.lifecycle.ViewModel
import com.beyond.imagetask.data.utils.Resource
import com.beyond.imagetask.presentation.utils.SingleLiveEvent


open class BaseViewModel : ViewModel() {

    var showLoading = SingleLiveEvent<Boolean>()
    var showApiError = SingleLiveEvent<String>()

}