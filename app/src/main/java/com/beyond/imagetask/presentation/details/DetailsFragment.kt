package com.beyond.imagetask.presentation.details

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.beyond.imagetask.R
import com.beyond.imagetask.databinding.FragmentDetailsBinding
import com.beyond.imagetask.domian.enums.FilterType
import com.beyond.imagetask.domian.models.ImagesModel
import com.beyond.imagetask.presentation.base.BasicFragment
import com.beyond.imagetask.presentation.utils.compressBitmap
import com.beyond.imagetask.presentation.utils.observe
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BasicFragment<FragmentDetailsBinding,
        DetailsViewModel>(FragmentDetailsBinding::inflate) {

    override val viewModel: DetailsViewModel by viewModels()

    private var originalImage: Bitmap? = null
    private var imageDetails: ImagesModel? = null

    override fun getFragmentArguments() {
        val args: DetailsFragmentArgs by navArgs()
        imageDetails = args.ImageModel
    }

    override fun FragmentDetailsBinding.initializeUI() {
        imageDetails?.let {
            Glide.with(requireContext())
                .load(it.url)
                .into(detailsImage)
            textDetailsDescription.text = it.description
            textTitle.text = it.name
            textLifeSpan.text = "${getString(R.string.life_span)} ${it.life_span}"
        }
    }

    override fun registerListeners() {
        binding.btnGrayScale.setOnClickListener {
            addFilter(FilterType.GRAY_SCALE)
        }

        binding.btnYellowish.setOnClickListener {
            addFilter(FilterType.YELLOW_WISH)
        }

    }

    override fun setupObservers() {
        observe(viewModel.filteredImageLiveData) { image ->
            image?.let { binding.detailsImage.setImageBitmap(it) }

        }
    }

    private fun addFilter(filterType: FilterType) {
        getBitmapFromImageView()
        compressBitmap(originalImage)
        originalImage?.let { it1 -> viewModel.applyFilter(it1, filterType) }
    }

    private fun getBitmapFromImageView(): Bitmap? {
        if (originalImage == null)
            originalImage = (binding.detailsImage.drawable as BitmapDrawable).bitmap

        return originalImage
    }

}