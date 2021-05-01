package com.golnaz.storyteltest.post.data.api

import com.golnaz.storyteltest.post.data.response.Comment
import com.golnaz.storyteltest.post.data.response.Photo
import com.golnaz.storyteltest.post.data.response.Post
import io.reactivex.Single
import javax.inject.Inject

class PostApiImpl @Inject constructor(private val postApi: PostApi) {
    fun gePost(): Single<List<Post>> {
        return postApi.gePost()
    }


    fun getPhoto(): Single<List<Photo>> {
        return postApi.getPhoto()
    }


    fun getPostComments(id: String): Single<List<Comment>> {
        return postApi.getPostComments(id)
    }
}