package com.golnaz.storyteltest.post.view.comment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.golnaz.storyteltest.post.data.response.Comment
import com.golnaz.storyteltest.post.domain.model.PostAndImageArg
import com.golnaz.storyteltest.post.domain.usecases.GetCommentsUseCase

class CommentViewModel @ViewModelInject constructor(
    private val getCommentsUseCase: GetCommentsUseCase
) : ViewModel() {
    private var _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>> get() = _comments
    val networkError = MutableLiveData<String>()
    val errors = MutableLiveData<String?>()


    private var _showProgressBar = MutableLiveData(true)
    val showProgressBar: LiveData<Boolean>
        get() = _showProgressBar

    val postDetail = MutableLiveData<PostAndImageArg>()

    fun getComments(id: String) {
        _showProgressBar.value = true
        getCommentsUseCase.execute(
            onSuccess = { commentList ->
                if (commentList.size >= 3) {
                    _comments.value = commentList.subList(0, 3)
                } else {
                    _comments.value = commentList
                }
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
            onFinish = {},
            params = id
        )
    }

}