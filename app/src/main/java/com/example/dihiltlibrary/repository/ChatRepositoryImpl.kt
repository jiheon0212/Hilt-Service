package com.example.dihiltlibrary.repository

import com.example.dihiltlibrary.data.ChatRoom
import com.example.dihiltlibrary.data.Message
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
): ChatRepository {
    override fun sendMessage(message: Message, room: String) {
        firebaseDatabase.reference.child("chat_rooms").child(room).child("messages").push().setValue(message)
    }

    override fun updateMessageList(room: String, callback: (MutableList<Message>) -> Unit) {
        firebaseDatabase.reference.child("chat_rooms").child(room).child("messages")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val messageList = mutableListOf<Message>()

                    snapshot.children.forEach {
                        val message = it.getValue(Message::class.java)!!
                        messageList.add(message)
                    }

                    callback(messageList)
                }
                override fun onCancelled(error: DatabaseError) {}
            })
    }

    override fun updateChatRoom(uid: String, callback: (MutableList<ChatRoom>) -> Unit) {
        firebaseDatabase.getReference("chat_rooms").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val chatroom = mutableListOf<ChatRoom>()

                snapshot.children.forEach {
                    if (it.key?.contains(uid)!!) {
                        val roomId = it.key!!
                        val roomDefaultName = roomId.replace(uid, "")

                        if (roomDefaultName != "") {
                            chatroom.add(ChatRoom(roomId, "$roomDefaultName's Message"))
                        } else {
                            chatroom.add(ChatRoom(roomId, "Self Message"))
                        }
                    }
                }

                callback(chatroom)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}