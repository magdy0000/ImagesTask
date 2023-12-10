package com.beyond.imagetask.data.network

import com.beyond.imagetask.data.models.ImagesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Services {


    @GET("search")
    suspend fun getImages(
        @Query("limit") limit: Int,
        @Query("has_breeds") hasBreeds: Boolean
    ): List<ImagesResponse>
}