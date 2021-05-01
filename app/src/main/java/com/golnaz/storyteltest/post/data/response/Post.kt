package com.golnaz.storyteltest.post.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post(
	@field:SerializedName("Posts")
	val post: List<PostItem>? = null
) : Parcelable

@Parcelize
data class PostItem(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("body")
	val body: String? = null,

	@field:SerializedName("userId")
	val userId: Int? = null
) : Parcelable
