package com.example.dihiltlibrary.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dihiltlibrary.data.UserBasicInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): FirebaseRepository {
    override fun uploadUserBasicInfo(user: UserBasicInfo): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        firestore.collection("users").document(user.id).set(user)
            .addOnCompleteListener { task ->
                result.value = task.isSuccessful
            }

        return result
    }
    override fun getUserBasicInfo(uid: String): LiveData<UserBasicInfo?> {
        val result = MutableLiveData<UserBasicInfo?>()
        firestore.collection("users").document(uid).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    result.value = task.result.toObject(UserBasicInfo::class.java)
                } else {
                    result.value = null
                }
            }

        return result
    }
    override suspend fun loginAnonymously(): LiveData<String> {
        val result = MutableLiveData<String>()
        val task = firebaseAuth.signInAnonymously().await()
        result.value = task.user?.uid

        return result
    }
}