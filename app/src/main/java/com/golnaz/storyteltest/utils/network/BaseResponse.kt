package com.golnaz.storyteltest.utils.network

import com.fasterxml.jackson.annotation.JsonProperty

data class BaseResponse(
    var statusCode: Int? = null,
    var success: Boolean = false,
    var message: String? = null
)
