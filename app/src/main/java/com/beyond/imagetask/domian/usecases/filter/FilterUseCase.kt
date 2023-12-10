package com.beyond.imagetask.domian.usecases.filter

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import com.beyond.imagetask.domian.enums.FilterType
import javax.inject.Inject

class FilterUseCase @Inject constructor() : IFilterUseCase {

    override fun execute(inputImage: Bitmap, filterType: FilterType): Bitmap {
        val outputBitmap =
            Bitmap.createBitmap(inputImage.width, inputImage.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(outputBitmap)
        val paint = Paint()
        val colorMatrix = ColorMatrix()
        if (filterType == FilterType.GRAY_SCALE) {

            colorMatrix.setSaturation(0f)
            val filter = ColorMatrixColorFilter(colorMatrix)
            paint.colorFilter = filter
            canvas.drawBitmap(inputImage, 0f, 0f, paint)
        } else if (filterType == FilterType.YELLOW_WISH) {

            colorMatrix.setScale(1f, 1f, 0.5f, 1f)
            val filter = ColorMatrixColorFilter(colorMatrix)
            paint.colorFilter = filter
            canvas.drawBitmap(inputImage, 0f, 0f, paint)
        }

        return outputBitmap
    }


}