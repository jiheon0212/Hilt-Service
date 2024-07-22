package com.example.dihiltlibrary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dihiltlibrary.AuthManager
import com.example.dihiltlibrary.data.Board
import com.example.dihiltlibrary.repository.BoardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val boardRepository: BoardRepository,
    private val authManager: AuthManager
): ViewModel() {
    val currentUserId: String? get() = authManager.currentUserId

    private val _uploadStatus = MutableLiveData<Boolean>()
    val uploadStatus: LiveData<Boolean> get() = _uploadStatus

    private val _boardList = MutableLiveData<MutableList<Board>>()
    val boardList: LiveData<MutableList<Board>> = _boardList

    fun writeBoard(board: Board) {
        boardRepository.writeBoard(board) { status ->
            _uploadStatus.value = status
        }
    }
    fun updateBoard(category: String) {
        viewModelScope.launch {
            boardRepository.updateBoard(category).collect { list ->
                _boardList.value = list
            }
        }
    }
}