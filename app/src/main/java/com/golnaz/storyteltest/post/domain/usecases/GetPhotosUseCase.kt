package com.golnaz.storyteltest.post.domain.usecases

import com.golnaz.storyteltest.post.data.response.Photo
import com.golnaz.storyteltest.post.domain.repository.PostRepository
import com.golnaz.storyteltest.utils.rx.DisposableSingleUseCaseNotParams
import io.reactivex.Single
import javax.inject.Inject

class GetPhotosUseCase @Inject constructor(private val postRepository: PostRepository) :
    DisposableSingleUseCaseNotParams<Photo>() {
    override fun buildUseCaseSingle(): Single<Photo> {
        return postRepository.getPhoto()
    }
}