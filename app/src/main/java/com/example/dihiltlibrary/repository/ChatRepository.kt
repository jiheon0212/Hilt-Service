package com.example.dihiltlibrary.repository

import com.example.dihiltlibrary.data.ChatRoom
import com.example.dihiltlibrary.data.Message

interface ChatRepository {
    fun sendMessage(message: Message, room: String)
    fun updateMessageList(room: String, callback: (MutableList<Message>) -> Unit)
    fun updateChatRoom(uid: String, callback: (MutableList<ChatRoom>) -> Unit)
}