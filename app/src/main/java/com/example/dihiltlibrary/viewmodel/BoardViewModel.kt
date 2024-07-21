package com.example.dihiltlibrary.viewmodel

import com.example.dihiltlibrary.AuthManager
import com.example.dihiltlibrary.repository.BoardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val boardRepository: BoardRepository,
    private val authManager: AuthManager
) {
}