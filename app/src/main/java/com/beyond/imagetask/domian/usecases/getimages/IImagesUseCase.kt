package com.beyond.imagetask.domian.usecases.getimages

import com.beyond.imagetask.domian.models.ImagesModel
import com.beyond.imagetask.data.utils.Resource

interface IImagesUseCase {

    suspend fun getImages(limit: Int, hasBreeds: Boolean): Resource<List<ImagesModel>>

}