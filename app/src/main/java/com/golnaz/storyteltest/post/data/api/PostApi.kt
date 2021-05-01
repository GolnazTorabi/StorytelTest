package com.golnaz.storyteltest.post.data.api

import com.golnaz.storyteltest.post.data.response.Comment
import com.golnaz.storyteltest.post.data.response.Photo
import com.golnaz.storyteltest.post.data.response.Post
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApi {
    @GET("/posts")
    fun gePost(): Single<Post>

    @GET("/photos")
    fun getPhoto(): Single<Photo>

    @GET("posts/{id}/comments")
    fun getPostComments(@Path("id") id: String): Single<Comment>
}