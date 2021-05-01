package com.golnaz.storyteltest.post.domain.repository

import com.golnaz.storyteltest.post.data.response.Comment
import com.golnaz.storyteltest.post.data.response.Photo
import com.golnaz.storyteltest.post.data.response.Post
import io.reactivex.Single

interface PostRepository {
    fun gePost(): Single<Post>
    fun getPhoto(): Single<Photo>
    fun getPostComments(id: String): Single<Comment>
}