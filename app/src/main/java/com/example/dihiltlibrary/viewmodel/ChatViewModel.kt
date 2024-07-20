package com.example.dihiltlibrary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dihiltlibrary.AuthManager
import com.example.dihiltlibrary.data.ChatRoom
import com.example.dihiltlibrary.data.Message
import com.example.dihiltlibrary.data.Post
import com.example.dihiltlibrary.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val authManager: AuthManager
): ViewModel() {
    private val _messageList = MutableLiveData<MutableList<Message>>()
    val messageList: LiveData<MutableList<Message>> get() = _messageList

    private val _roomIds = MutableLiveData<MutableList<ChatRoom>>()
    val roomIds: LiveData<MutableList<ChatRoom>> get() = _roomIds

    val currentUserId: String? get() = authManager.currentUserId

    fun sendMessage(message: Message, room: String) {
        chatRepository.sendMessage(message, room)
    }
    fun updateMessageList(room: String) {
        chatRepository.updateMessageList(room) { list ->
            _messageList.value = list
        }
    }
    fun updateChatRoom(uid: String) {
        chatRepository.updateChatRoom(uid) { list ->
            _roomIds.value = list
        }
    }
}