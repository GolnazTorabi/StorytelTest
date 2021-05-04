package com.golnaz.storyteltest.post.view.post

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.golnaz.storyteltest.post.data.response.Post
import com.golnaz.storyteltest.post.domain.model.PostAndImages
import com.golnaz.storyteltest.post.domain.usecases.GetPhotosUseCase
import com.golnaz.storyteltest.post.domain.usecases.GetPostsUseCase
import dagger.hilt.android.qualifiers.ApplicationContext

class PostViewModel @ViewModelInject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val getPhotosUseCase: GetPhotosUseCase
) : ViewModel() {
    private var _postsAndPhotos = MutableLiveData<PostAndImages>()
    val postsAndPhotos: LiveData<PostAndImages> get() = _postsAndPhotos
    val errors = MutableLiveData<String?>()
    val networkError = MutableLiveData<String>()

    private var _showProgressBar = MutableLiveData(true)
    val showProgressBar: LiveData<Boolean>
        get() = _showProgressBar

    @RequiresApi(Build.VERSION_CODES.M)
    fun getPosts() {
        _showProgressBar.value = true
        getPostsUseCase.execute(
            onSuccess = { posts ->
                getPhotos(posts)
            },
            onError = { error ->
                if (error.statusCode == 500) {
                    networkError.value = error.message ?: ""
                    _showProgressBar.value = false
                } else {
                    errors.value = error.message ?: ""
                    _showProgressBar.value = false
                }

            },
            onFinish = {}
        )
    }

    fun getPhotos(posts: List<Post>) {
        _showProgressBar.value = true
        getPhotosUseCase.execute(
            onSuccess = { photos ->
                _postsAndPhotos.value = PostAndImages(posts, photos)
                _showProgressBar.value = false
            },
            onError = { error ->
                if (error.statusCode == 500) {
                    networkError.value = error.message ?: ""
                    _showProgressBar.value = false
                } else {
                    errors.value = error.message ?: ""
                    _showProgressBar.value = false
                }
            },
            onFinish = {}
        )
    }
}