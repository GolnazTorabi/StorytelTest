package com.golnaz.storyteltest.utils.network

data class BaseResponse(
    var statusCode: Int? = null,
    var success: Boolean = false,
    var message: String? = null
)
