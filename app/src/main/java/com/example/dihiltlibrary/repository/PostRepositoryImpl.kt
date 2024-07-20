package com.example.dihiltlibrary.repository

import com.example.dihiltlibrary.GetTime.getCurrentDay
import com.example.dihiltlibrary.data.CurrentStatus
import com.example.dihiltlibrary.data.Post
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val firestore: FirebaseFirestore
): PostRepository {
    override fun uploadPost(post: Post, callback: (Boolean) -> Unit){
        firebaseDatabase.reference.child("post").child(getCurrentDay()).push().setValue(post)
            .addOnCompleteListener { task ->
                callback(task.isSuccessful)
            }
    }

    // child event listener는 변경이 일어난 항목에 대해서만 update가 진행되므로 post list 갱신을 위해서는 value event listener를 사용하는 것이 맞다.
    override fun updatePost(callback: (MutableList<Post>) -> Unit) {
        firebaseDatabase.reference.child("post").child(getCurrentDay()).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val postList = mutableListOf<Post>()

                snapshot.children.forEach { dataSnapshot ->
                    val post = dataSnapshot.getValue(Post::class.java)
                    post?.let { postList.add(it) }
                }

                callback(postList)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun getUserStatus(callback: (MutableList<CurrentStatus>) -> Unit) {
        firebaseDatabase.reference.child("users").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val statusList = mutableListOf<CurrentStatus>()

                dataSnapshot.children.forEach { snapshot ->
                    val data = snapshot.getValue(CurrentStatus::class.java)!!
                    statusList.add(data)
                }

                callback(statusList)
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}