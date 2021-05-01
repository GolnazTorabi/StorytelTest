package com.golnaz.storyteltest.post.domain.repository

import com.golnaz.storyteltest.post.data.response.Comment
import com.golnaz.storyteltest.post.data.response.Photo
import com.golnaz.storyteltest.post.data.response.Post
import io.reactivex.Single

interface PostRepository {
    fun gePost(): Single<List<Post>>
    fun getPhoto(): Single<List<Photo>>
    fun getPostComments(id: String): Single<List<Comment>>
}