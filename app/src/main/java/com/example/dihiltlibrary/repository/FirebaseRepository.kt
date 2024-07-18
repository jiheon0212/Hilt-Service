package com.example.dihiltlibrary.repository

import androidx.lifecycle.LiveData
import com.example.dihiltlibrary.data.UserBasicInfo

interface FirebaseRepository {
    fun uploadUserBasicInfo(user: UserBasicInfo): LiveData<Boolean>
    fun getUserBasicInfo(uid: String): LiveData<UserBasicInfo?>
    suspend fun loginAnonymously(): LiveData<String>
}