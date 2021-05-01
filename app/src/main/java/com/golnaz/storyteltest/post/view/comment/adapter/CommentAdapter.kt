package com.golnaz.storyteltest.post.view.comment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.golnaz.storyteltest.R
import com.golnaz.storyteltest.databinding.CommentItemBinding
import com.golnaz.storyteltest.post.data.response.Comment
import javax.inject.Inject

class CommentAdapter @Inject constructor() :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    private var items: MutableList<Comment> = mutableListOf()

    private var layoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val binding: CommentItemBinding = DataBindingUtil.inflate(
            layoutInflater!!,
            R.layout.comment_item,
            parent,
            false
        )
        return CommentViewHolder(binding)
    }

    fun fillData(items: MutableList<Comment>) {
        this.items.clear()
        this.items = items
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.fillData(items[position])
    }

    override fun getItemCount(): Int = items.size


    inner class CommentViewHolder(val binding: CommentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun fillData(comment: Comment) {
            binding.comment = comment
        }

    }
}