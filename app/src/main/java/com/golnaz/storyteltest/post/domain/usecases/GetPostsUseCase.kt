package com.golnaz.storyteltest.post.domain.usecases

import com.golnaz.storyteltest.post.data.response.Post
import com.golnaz.storyteltest.post.domain.repository.PostRepository
import com.golnaz.storyteltest.utils.rx.DisposableSingleUseCaseNotParams
import io.reactivex.Single
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val postRepository: PostRepository) :
    DisposableSingleUseCaseNotParams<Post>() {
    override fun buildUseCaseSingle(): Single<Post> {
        return postRepository.gePost()
    }
}