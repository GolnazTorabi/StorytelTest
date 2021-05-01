package com.golnaz.storyteltest.post.data.response

import com.fasterxml.jackson.annotation.JsonProperty
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(
	@field:SerializedName("Photos")
	val photos: List<PhotosItem>? = null
) : Parcelable

@Parcelize
data class PhotosItem(

	@field:SerializedName("albumId")
	val albumId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("thumbnailUrl")
	val thumbnailUrl: String? = null
) : Parcelable
