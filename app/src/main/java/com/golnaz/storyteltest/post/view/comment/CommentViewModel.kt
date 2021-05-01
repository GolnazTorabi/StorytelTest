package com.golnaz.storyteltest.post.view.comment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.golnaz.storyteltest.post.data.response.Comment
import com.golnaz.storyteltest.post.domain.usecases.GetCommentsUseCase
import com.golnaz.storyteltest.utils.network.NetworkConnection
import javax.inject.Inject

class CommentViewModel @ViewModelInject constructor(
    private val getCommentsUseCase: GetCommentsUseCase
) : ViewModel() {
    private var _comments = MutableLiveData<Comment>()
    val comments: LiveData<Comment> get() = _comments
    val networkError = MutableLiveData<String>()
    val errors = MutableLiveData<String?>()

    var _showProgressBar = MutableLiveData(true)
    val showProgressBar: LiveData<Boolean>
        get() = _showProgressBar

    fun getComments(id: String) {
        _showProgressBar.value = true
        if (NetworkConnection.hasNetwork()) {
            getCommentsUseCase.execute(
                onSuccess = { commentList ->
                    _comments.value = commentList
                    _showProgressBar.value = false
                },
                onError = { error ->
                    errors.value = error.message ?: ""
                    _showProgressBar.value = false
                },
                onFinish = {},
                params = id
            )
        } else {
            _showProgressBar.value = false
            networkError.value = "Network error please try again"
        }
    }

}