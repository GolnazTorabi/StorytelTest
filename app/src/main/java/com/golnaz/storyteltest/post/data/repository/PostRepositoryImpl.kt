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
    override fun getPost(): Single<List<Post>> {
        return postApiImpl.gePost()
    }

    override fun getPhoto(): Single<List<Photo>> {
        return postApiImpl.getPhoto()
    }

    override fun getPostComments(id: String): Single<List<Comment>> {
        return postApiImpl.getPostComments(id)
    }
}