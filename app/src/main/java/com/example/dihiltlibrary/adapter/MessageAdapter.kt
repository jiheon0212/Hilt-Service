package com.example.dihiltlibrary.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dihiltlibrary.data.Message
import com.example.dihiltlibrary.databinding.MessageReceiverBinding
import com.example.dihiltlibrary.databinding.MessageSenderBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MessageAdapter(private var messageList: MutableList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_SEND = 1
    private val VIEW_TYPE_RECEIVED = 2
    private val auth = Firebase.auth

    @SuppressLint("NotifyDataSetChanged")
    fun fetchMessage(newList: MutableList<Message>) {
        messageList = newList
        notifyDataSetChanged()
    }

    inner class SenderViewHolder(val binding: MessageSenderBinding): RecyclerView.ViewHolder(binding.root)
    inner class ReceiverViewHolder(val binding: MessageReceiverBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val sender = MessageSenderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val receiver = MessageReceiverBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return if (viewType == VIEW_TYPE_SEND) {
            SenderViewHolder(sender)
        } else {
            ReceiverViewHolder(receiver)
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val messageData = messageList[position]
        val (sender, message, timestamp) = messageData

        if (holder.itemViewType == VIEW_TYPE_SEND){
            (holder as SenderViewHolder).binding.apply {
                tvUser.text = sender
                tvMessage.text = message
                tvCurrentTime.text = timestamp
            }
        } else {
            (holder as ReceiverViewHolder).binding.apply {
                tvUser.text = sender
                tvMessage.text = message
                tvCurrentTime.text = timestamp
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        // messageData 객체에 담긴 uid를 통해 sender인지 recevier인지 구분한다.
        return if (messageList[position].sender == auth.uid) VIEW_TYPE_SEND else VIEW_TYPE_RECEIVED
    }
    override fun getItemCount(): Int = messageList.size
}