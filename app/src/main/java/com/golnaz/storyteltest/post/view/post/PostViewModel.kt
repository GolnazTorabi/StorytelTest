package com.golnaz.storyteltest.post.view.post

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.golnaz.storyteltest.post.data.response.Post
import com.golnaz.storyteltest.post.domain.model.PostAndImages
import com.golnaz.storyteltest.post.domain.usecases.GetPhotosUseCase
import com.golnaz.storyteltest.post.domain.usecases.GetPostsUseCase
import com.golnaz.storyteltest.utils.network.NetworkConnection
import javax.inject.Inject

class PostViewModel @ViewModelInject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val getPhotosUseCase: GetPhotosUseCase
) : ViewModel() {
    private var _postsAndPhotos = MutableLiveData<PostAndImages>()
    val postsAndPhotos: LiveData<PostAndImages> get() = _postsAndPhotos
    val networkError = MutableLiveData<String>()
    val errors = MutableLiveData<String?>()

    var _showProgressBar = MutableLiveData(true)
    val showProgressBar: LiveData<Boolean>
        get() = _showProgressBar

    fun getPosts() {
        if (NetworkConnection.hasNetwork()) {
            _showProgressBar.value = true
            getPostsUseCase.execute(
                onSuccess = { posts ->
                    getPhotos(posts)
                },
                onError = { error ->
                    Log.d("TAG", "getPosts: "+error.statusCode)
                    errors.value = error.message ?: ""
                    _showProgressBar.value = false
                },
                onFinish = {}
            )
        } else {
            _showProgressBar.value = false
            networkError.value = "Network error please try again"
        }
    }

    private fun getPhotos(posts: Post) {
        if (NetworkConnection.hasNetwork()) {
            _showProgressBar.value = true
            getPhotosUseCase.execute(
                onSuccess = { photos ->
                    _postsAndPhotos.value = PostAndImages(posts, photos)
                    _showProgressBar.value = false
                },
                onError = { error ->
                    errors.value = error.message ?: ""
                    _showProgressBar.value = false
                },
                onFinish = {}
            )
        } else {
            networkError.value = "Network error please try again"
            _showProgressBar.value = false
        }
    }
}