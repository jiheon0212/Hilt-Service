package com.example.dihiltlibrary.repository

import com.example.dihiltlibrary.GetTime.getCurrentDay
import com.example.dihiltlibrary.data.Post
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class BoardRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
): BoardRepository {
    override fun uploadPost(post: Post, callback: (Boolean) -> Unit){
        firebaseDatabase.reference.child("post").child(getCurrentDay()).push().setValue(post)
            .addOnCompleteListener { task ->
                callback(task.isSuccessful)
            }
    }

    override fun updatePost(callback: (MutableList<Post>) -> Unit) {
        firebaseDatabase.reference.child("post").addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val postList = mutableListOf<Post>()

                snapshot.children.forEach { dataSnapshot ->
                    val post = dataSnapshot.getValue(Post::class.java)
                    post?.let { postList.add(it) }
                }

                callback(postList)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}