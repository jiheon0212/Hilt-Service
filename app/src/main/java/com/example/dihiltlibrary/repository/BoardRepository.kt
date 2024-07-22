package com.example.dihiltlibrary.repository

import com.example.dihiltlibrary.data.Board
import kotlinx.coroutines.flow.Flow

interface BoardRepository {
    // title을 통해서 해당 board data 불러와 board result fragment에 띄워주는 메서드
    // board를 firestore를 통해 업로드하는 메서드
    // board를 firestore를 통해 addSnapshotListener로 실시간 업데이트 하는 메서드
    fun writeBoard(board: Board, callback: (Boolean) -> Unit)
    fun updateBoard(): Flow<MutableList<Board>>
    fun getBoard()
}