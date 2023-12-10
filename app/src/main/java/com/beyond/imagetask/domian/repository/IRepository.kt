package com.beyond.imagetask.domian.repository

import com.beyond.imagetask.domian.models.ImagesModel
import retrofit2.http.Query

interface IRepository {



    suspend fun getImages(limit: Int, hasBreeds: Boolean): List<ImagesModel>
}