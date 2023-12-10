package com.beyond.imagetask.presentation.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.media.ExifInterface
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException

fun Fragment.showToast(message: Any?) {
    Toast.makeText(requireContext(), "$message", Toast.LENGTH_LONG).show()
}

fun <T> Fragment.getNavigationResultLiveData(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)

fun <T> Fragment.removeNavigationResultObserver(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.remove<T>(key)

fun <T> Fragment.setNavigationResult(result: T, key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}

fun Fragment.onBackPressedCustomAction(action: () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(
        viewLifecycleOwner,
        object : OnBackPressedCallback(true) {
            override
            fun handleOnBackPressed() {
                action()
            }
        }
    )
}


fun Fragment.backToPreviousScreen() {
    findNavController().navigateUp()
}


fun <T> Fragment.observe(liveData: LiveData<T>, block: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner) {
        block.invoke(it)
    }
}




fun Fragment.compressBitmap(originalBitmap: Bitmap?): Bitmap? {
    // Ensure a valid bitmap is provided
    if (originalBitmap == null) {
        return null
    }

    // The new size we want to scale to
    val REQUIRED_SIZE = 1024

    // Find the correct scale value. It should be the power of 2.
    var width_tmp = originalBitmap.width
    var height_tmp = originalBitmap.height
    var scale = 1
    while (true) {
        if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE) break
        width_tmp /= 2
        height_tmp /= 2
        scale *= 2
    }

    // Decode with inSampleSize
    val o2 = BitmapFactory.Options()
    o2.inSampleSize = scale
    var compressedBitmap = Bitmap.createScaledBitmap(
        originalBitmap,
        originalBitmap.width / scale,
        originalBitmap.height / scale,
        false
    )

    val exifOrientation: Int = getExifOrientation(originalBitmap)

    var rotate = 0f
    when (exifOrientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> rotate = 90f
        ExifInterface.ORIENTATION_ROTATE_180 -> rotate = 180f
        ExifInterface.ORIENTATION_ROTATE_270 -> rotate = 270f
    }

    if (rotate != 0f) {
        val w = compressedBitmap.width
        val h = compressedBitmap.height

        // Setting pre rotate
        val mtx = Matrix()
        mtx.preRotate(rotate)

        // Rotating Bitmap & convert to ARGB_8888, required by tess
        compressedBitmap = Bitmap.createBitmap(compressedBitmap, 0, 0, w, h, mtx, false)
    }

    return compressedBitmap.copy(Bitmap.Config.ARGB_8888, true)
}

private fun Fragment.getExifOrientation(bitmap: Bitmap): Int {
    val exif: ExifInterface
    return try {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val inputStream = ByteArrayInputStream(byteArray)

        exif = ExifInterface(inputStream)
        exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
    } catch (e: IOException) {
        ExifInterface.ORIENTATION_NORMAL
    }
}

