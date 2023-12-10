package com.beyond.imagetask.presentation.home

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.beyond.imagetask.databinding.FragmentHomeBinding
import com.beyond.imagetask.domian.models.ImagesModel
import com.beyond.imagetask.presentation.adapters.ImagesAdapter
import com.beyond.imagetask.presentation.base.BasicFragment
import com.beyond.imagetask.presentation.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BasicFragment<FragmentHomeBinding,HomeViewModel>(FragmentHomeBinding::inflate) {

    private val imagesAdapter : ImagesAdapter by lazy { ImagesAdapter() }
    override val viewModel: HomeViewModel by viewModels ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getImages()
    }

    override fun registerListeners() {
        imagesAdapter.setOnClick {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it))
        }
    }

    override fun setupObservers() {
        super.setupObservers()
       observe(viewModel.imageLiveData) {
           it?.let {
               imagesAdapter.images = it as? ArrayList<ImagesModel>
               binding.recyclerImages.adapter = imagesAdapter
           }
       }
    }

}