package com.golnaz.storyteltest.post.domain.model

import com.golnaz.storyteltest.post.data.response.Photo
import com.golnaz.storyteltest.post.data.response.Post

data class PostAndImages(
    var posts: Post,
    var photos: Photo
)
