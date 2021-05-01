package com.golnaz.storyteltest.post.domain.usecases

import com.golnaz.storyteltest.post.data.response.Comment
import com.golnaz.storyteltest.post.domain.repository.PostRepository
import com.golnaz.storyteltest.utils.rx.DisposableSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(private val postRepository: PostRepository) :
    DisposableSingleUseCase<Comment, String>() {
    override fun buildUseCaseSingle(params: String): Single<Comment> {
        return postRepository.getPostComments(params)
    }
}