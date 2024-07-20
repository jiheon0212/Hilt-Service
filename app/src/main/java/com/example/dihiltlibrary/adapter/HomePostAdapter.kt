package com.example.dihiltlibrary.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dihiltlibrary.data.Post
import com.example.dihiltlibrary.databinding.PostAdapterBinding

class HomePostAdapter(private var postList: MutableList<Post>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class PostViewHolder(val binding: PostAdapterBinding): RecyclerView.ViewHolder(binding.root)
    @SuppressLint("NotifyDataSetChanged")
    fun fetchPost(newList: MutableList<Post>) {
        postList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val post = PostAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(post)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val postValue = postList[position]
        val (timestamp, writer, content, _, _) = postValue
        val starsCount = postValue.starsCount
        val replyCount = postValue.replyCount

        (holder as PostViewHolder).binding.apply {
            postWriter.text = writer
            postTimestamp.text = timestamp
            postTitle.text = content?.contentName
            // postImage config with glide
            postText.text = content?.contentText
            postStarCount.text = starsCount.toString()
            postReplyCount.text = replyCount.toString()
        }
    }
    override fun getItemCount(): Int = postList.size
}