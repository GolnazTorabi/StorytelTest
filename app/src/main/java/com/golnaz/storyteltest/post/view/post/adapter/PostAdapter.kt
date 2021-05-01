package com.golnaz.storyteltest.post.view.post.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.golnaz.storyteltest.R
import com.golnaz.storyteltest.databinding.PostItemBinding
import com.golnaz.storyteltest.post.data.response.Photo
import com.golnaz.storyteltest.post.data.response.Post
import com.golnaz.storyteltest.post.domain.model.PostAndImageArg
import com.golnaz.storyteltest.post.domain.model.PostAndImages
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class PostAdapter @Inject constructor() : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    private val clickSubjectComment = PublishSubject.create<PostAndImageArg>()
    private var items: PostAndImages? = null

    private var layoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val binding: PostItemBinding = DataBindingUtil.inflate(
            layoutInflater!!,
            R.layout.post_item,
            parent,
            false
        )
        return PostViewHolder(binding)
    }

    fun fillData(items: PostAndImages) {
        this.items = null
        this.items = items
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        items?.posts?.get(position)?.let {
            items?.photos?.get(position)?.let { it1 ->
                holder.fillData(
                    it,
                    it1
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return if (items == null || items?.photos == null) {
            0
        } else
            items?.posts?.size ?: 0
    }

    val clickEventComment: Observable<PostAndImageArg> = clickSubjectComment

    inner class PostViewHolder(val binding: PostItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun fillData(post: Post, photo: Photo) {
            binding.photo = photo
            binding.post = post

            binding.layout.setOnClickListener {
                clickSubjectComment.onNext(PostAndImageArg(post, photo))
            }
        }


    }
}