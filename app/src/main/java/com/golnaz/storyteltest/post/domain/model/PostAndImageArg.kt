package com.golnaz.storyteltest.post.domain.model

import android.os.Parcelable
import com.golnaz.storyteltest.post.data.response.Photo
import com.golnaz.storyteltest.post.data.response.Post
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostAndImageArg(
    var posts: Post,
    var photos: Photo
):Parcelable
