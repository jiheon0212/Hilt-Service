package com.example.dihiltlibrary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dihiltlibrary.data.UserBasicInfo
import com.example.dihiltlibrary.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FirebaseLoginViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
): ViewModel() {

    private val _userUploadStatus = MutableLiveData<Boolean>()
    val userUploadStatus: LiveData<Boolean> get() = _userUploadStatus

    private val _userBasicInfo = MutableLiveData<UserBasicInfo?>()
    val userBasicInfo: LiveData<UserBasicInfo?> get() = _userBasicInfo

    private val _userUid = MutableLiveData<String>()
    val userUid: LiveData<String> get() = _userUid

    fun uploadUserBasicInfo(user: UserBasicInfo) {
        val uploadResult = firebaseRepository.uploadUserBasicInfo(user)
        uploadResult.observeForever { status ->
            _userUploadStatus.value = status
        }
    }

    fun getUserBasicInfo(uid: String) {
        viewModelScope.launch {
            val userBasicInfo = firebaseRepository.getUserBasicInfo(uid)
            withContext(Dispatchers.Main) {
                _userBasicInfo.value = userBasicInfo
            }
        }
    }

    fun loginAnonymously() {
        viewModelScope.launch {
            val authResult = withContext(Dispatchers.Main) {
                firebaseRepository.loginAnonymously()
            }
            authResult.observeForever { user ->
                _userUid.value = user
            }
        }
    }
}