package com.example.dihiltlibrary.repository

import com.example.dihiltlibrary.data.CurrentStatus
import com.example.dihiltlibrary.data.Post

interface PostRepository {
    // 게시물 업로드
    // 게시물 변경 이벤트 처리
    // 유저 온라인 상태를 구분하는 상단 아이콘
    fun uploadPost(post: Post, callback: (Boolean) -> Unit)
    fun updatePost(callback: (MutableList<Post>) -> Unit)
    fun getUserStatus(callback: (MutableList<CurrentStatus>) -> Unit)
}