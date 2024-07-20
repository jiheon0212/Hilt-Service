package com.example.dihiltlibrary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dihiltlibrary.AuthManager
import com.example.dihiltlibrary.data.Post
import com.example.dihiltlibrary.repository.BoardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val boardRepository: BoardRepository,
    private val authManager: AuthManager
): ViewModel() {
    private val _post = MutableLiveData<MutableList<Post>>()
    val post: LiveData<MutableList<Post>> get() = _post

    private val _uploadStatus = MutableLiveData<Boolean>()
    val uploadStatus: LiveData<Boolean> get() = _uploadStatus
    val currentUserId: String? get() = authManager.currentUserId
    init {
        updatePost()
    }

    fun uploadPost(post: Post) {
        boardRepository.uploadPost(post) { status ->
            _uploadStatus.value = status
        }
    }
    private fun updatePost() {
        boardRepository.updatePost { list ->
            _post.value = list
        }
    }
}