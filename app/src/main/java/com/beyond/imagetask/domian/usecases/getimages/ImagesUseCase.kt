package com.beyond.imagetask.domian.usecases.getimages

import com.beyond.imagetask.domian.models.ImagesModel
import com.beyond.imagetask.domian.repository.IRepository
import com.beyond.imagetask.data.utils.Resource
import javax.inject.Inject

class ImagesUseCase @Inject constructor(private val repository: IRepository) : IImagesUseCase {

    override suspend fun getImages(limit: Int, hasBreeds: Boolean): Resource<List<ImagesModel>> {
        return try {
            val images = repository.getImages(limit, hasBreeds)
            Resource.success(images)
        } catch (e: Exception) {
            Resource.error(e)
        }
    }
}