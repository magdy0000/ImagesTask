package com.beyond.imagetask.domian.usecases.filter

import android.graphics.Bitmap
import com.beyond.imagetask.domian.enums.FilterType

interface IFilterUseCase {

    fun execute(inputImage:Bitmap , filterType: FilterType):Bitmap

}