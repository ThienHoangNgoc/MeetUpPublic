package com.example.meetupapp.app_extensions

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.getColorFromRes(colorID: Int): Int {
    return ContextCompat.getColor(this, colorID)
}

fun Drawable.changeBackground(@ColorInt colorInt: Int){
    when (this) {
        is GradientDrawable -> setColor(colorInt)
        is ShapeDrawable -> paint.color = colorInt
        is ColorDrawable -> color = colorInt
    }
}
