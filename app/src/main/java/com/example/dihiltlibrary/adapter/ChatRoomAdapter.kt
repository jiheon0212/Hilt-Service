package com.example.dihiltlibrary.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dihiltlibrary.data.ChatRoom
import com.example.dihiltlibrary.databinding.ChatRoomBinding

class ChatRoomAdapter(private val onItemClick: (String) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class ChatroomViewHolder(val binding: ChatRoomBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val roomId = chatroomList[adapterPosition].roomId
                    onItemClick(roomId)
                }
            }
        }
    }
    var chatroomList = mutableListOf<ChatRoom>()

    // livedata를 통해 전달받은 chatroomId로 adapter를 갱신시켜준다.
    @SuppressLint("NotifyDataSetChanged")
    fun fetchChatroom(newList: MutableList<ChatRoom>) {
        chatroomList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ChatRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatroomViewHolder(binding)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val roomName = chatroomList[position].roomName
        (holder as ChatroomViewHolder).binding.tvVisibleName.text = roomName
    }
    override fun getItemCount(): Int = chatroomList.size
}