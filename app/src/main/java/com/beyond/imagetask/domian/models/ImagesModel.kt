package com.beyond.imagetask.domian.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImagesModel(
    val id: String?,
    val url: String?,
    val description: String?,
    val life_span: String?,
    val name: String?,
) : Parcelable