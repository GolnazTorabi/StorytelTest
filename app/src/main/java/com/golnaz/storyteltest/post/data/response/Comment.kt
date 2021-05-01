package com.golnaz.storyteltest.post.data.response

import com.fasterxml.jackson.annotation.JsonProperty
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comment(

	@field:SerializedName("Comment")
	val comment: List<CommentItem>? = null
) : Parcelable

@Parcelize
data class CommentItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("postId")
	val postId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("body")
	val body: String? = null,

	@field:SerializedName("email")
	val email: String? = null
) : Parcelable
