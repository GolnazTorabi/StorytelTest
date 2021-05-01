package com.golnaz.storyteltest.utils.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter(value = ["imageUrl", "placeHolder"], requireAll = false)
fun ImageView.loadImageUrl(imageUrl: String?, placeholder: Drawable) {
    Glide.with(this)
        .load(imageUrl?.trim())
        .placeholder(placeholder)
        .thumbnail(
            Glide.with(this)
                .load(placeholder)
        )
        .error(placeholder)
        .into(this)
}