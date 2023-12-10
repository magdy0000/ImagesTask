package com.beyond.imagetask.presentation.base
 
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.beyond.imagetask.presentation.utils.observe
import com.beyond.imagetask.presentation.utils.showToast

abstract class BasicFragment<VB : ViewBinding, VM : BaseViewModel>(
    override val bindingInflater: (LayoutInflater) -> VB
) : BaseFragment<VB>(bindingInflater) {
 
    protected abstract val viewModel: VM

    open val fullScreenViewGroup: ViewGroup
        get() = binding.root as ViewGroup
 
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
 
        setUpBaseObservers()
    }
 
 
    private fun setUpBaseObservers() {
        observe(viewModel.showLoading) { showLoading ->
            if (showLoading) {
                showLoading()
            } else {
                hideLoading()
            }
        }
 
        observe(viewModel.showApiError) { failureResource ->
          showToast(failureResource)
        }
    }
 
 
}