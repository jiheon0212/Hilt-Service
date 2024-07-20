package com.example.dihiltlibrary.repository

import androidx.lifecycle.LiveData
import com.example.dihiltlibrary.data.UserBasicInfo

interface FirebaseRepository {
    fun uploadUserBasicInfo(user: UserBasicInfo): LiveData<Boolean>
    suspend fun getUserBasicInfo(uid: String): UserBasicInfo?
    suspend fun loginAnonymously(): LiveData<String>
}