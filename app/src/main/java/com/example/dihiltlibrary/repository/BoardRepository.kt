package com.example.dihiltlibrary.repository

import com.example.dihiltlibrary.data.Post

interface BoardRepository {
    // 게시물 업로드
    // 게시물 변경 이벤트 처리
    fun uploadPost(post: Post, callback: (Boolean) -> Unit)
    fun updatePost(callback: (MutableList<Post>) -> Unit)
}