package com.golnaz.storyteltest.post.data.api

import com.golnaz.storyteltest.post.data.response.Comment
import com.golnaz.storyteltest.post.data.response.Photo
import com.golnaz.storyteltest.post.data.response.Post
import io.reactivex.Single
import javax.inject.Inject

class PostApiImpl @Inject constructor(private val postApi: PostApi) {
    fun gePost(): Single<Post> {
        return postApi.gePost()
    }


    fun getPhoto(): Single<Photo> {
        return postApi.getPhoto()
    }


    fun getPostComments(id: String): Single<Comment> {
        return postApi.getPostComments(id)
    }
}