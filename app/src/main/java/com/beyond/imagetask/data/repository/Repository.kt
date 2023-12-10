package com.beyond.imagetask.data.repository

import com.beyond.imagetask.data.network.Services
import com.beyond.imagetask.domian.mapper.mapToDomain
import com.beyond.imagetask.domian.models.ImagesModel
import com.beyond.imagetask.domian.repository.IRepository
import javax.inject.Inject

class Repository @Inject constructor (val services: Services) : IRepository {


    override suspend fun getImages(limit: Int, hasBreeds: Boolean): List<ImagesModel> {
        return services.getImages(limit,hasBreeds).map { it.mapToDomain() }
    }
}