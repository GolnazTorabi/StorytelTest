package com.golnaz.storyteltest.post.data.repository

import com.golnaz.storyteltest.post.data.api.PostApiImpl
import com.golnaz.storyteltest.post.data.response.Comment
import com.golnaz.storyteltest.post.data.response.Photo
import com.golnaz.storyteltest.post.data.response.Post
import com.golnaz.storyteltest.post.domain.repository.PostRepository
import io.reactivex.Single
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val postApiImpl: PostApiImpl) :
    PostRepository {
    override fun gePost(): Single<Post> {
        return postApiImpl.gePost()
    }

    override fun getPhoto(): Single<Photo> {
        return postApiImpl.getPhoto()
    }

    override fun getPostComments(id: String): Single<Comment> {
        return postApiImpl.getPostComments(id)
    }
}