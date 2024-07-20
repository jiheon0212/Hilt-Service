package com.example.dihiltlibrary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dihiltlibrary.AuthManager
import com.example.dihiltlibrary.data.CurrentStatus
import com.example.dihiltlibrary.data.Post
import com.example.dihiltlibrary.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val authManager: AuthManager
): ViewModel() {
    private val _post = MutableLiveData<MutableList<Post>>()
    val post: LiveData<MutableList<Post>> get() = _post

    private val _uploadStatus = MutableLiveData<Boolean>()
    val uploadStatus: LiveData<Boolean> get() = _uploadStatus

    private val _currentStatus = MutableLiveData<MutableList<CurrentStatus>>()
    val currentStatus: LiveData<MutableList<CurrentStatus>> get() = _currentStatus
    val currentUserId: String? get() = authManager.currentUserId
    init {
        updatePost()
    }

    fun uploadPost(post: Post) {
        postRepository.uploadPost(post) { status ->
            _uploadStatus.value = status
        }
    }
    private fun updatePost() {
        postRepository.updatePost { list ->
            _post.value = list
        }
    }

    fun getUserStatus() {
        postRepository.getUserStatus { list ->
            _currentStatus.value = list
        }
    }
}