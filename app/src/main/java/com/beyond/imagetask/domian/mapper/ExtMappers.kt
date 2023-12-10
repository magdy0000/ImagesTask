package com.beyond.imagetask.domian.mapper

import com.beyond.imagetask.data.models.ImagesResponse
import com.beyond.imagetask.domian.models.ImagesModel

fun ImagesResponse.mapToDomain(): ImagesModel {
    val mBreeds = if (!breeds.isNullOrEmpty()) breeds[0] else null
    return ImagesModel(
        id = id,
        url = url,
        description = mBreeds?.description,
        life_span = mBreeds?.life_span,
        name = mBreeds?.name
    )
}

