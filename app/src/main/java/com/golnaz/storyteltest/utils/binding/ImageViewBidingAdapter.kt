package com.golnaz.storyteltest.utils.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso


@BindingAdapter(value = ["imageUrl", "placeHolder"], requireAll = false)
fun ImageView.loadImageUrl(imageUrl: String?, placeholder: Drawable) {
    Picasso.get()
        .load(imageUrl)
        .error(placeholder)
        .placeholder(placeholder)
        .into(this)
}