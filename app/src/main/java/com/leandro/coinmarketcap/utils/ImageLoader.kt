package com.leandro.coinmarketcap.utils

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.os.Build
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.leandro.coinmarketcap.R


fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.colorFilter = BlendModeColorFilter(
                ContextCompat.getColor(
                    context,
                    R.color.red
                ), BlendMode.SRC_IN
            )
        } else {
            this.setColorFilter(
                ContextCompat.getColor(context, R.color.red),
                PorterDuff.Mode.SRC_IN
            )
        }
        start()
    }
}

fun ImageView.loadImage(uri: String?, progressDrawble: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawble)
        .error(R.drawable.ic_image_error_grey_24)
    Glide.with(context).setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}

/*@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView, url: String) {
    view.loadImage(url, getProgressDrawable(view.context))
}*/