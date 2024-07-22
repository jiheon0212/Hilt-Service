package com.example.dihiltlibrary.repository

import android.util.Log
import com.example.dihiltlibrary.GetTime.getCurrentDay
import com.example.dihiltlibrary.GetTime.getCurrentTime
import com.example.dihiltlibrary.data.Board
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class BoardRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): BoardRepository {
    override fun writeBoard(board: Board, callback: (Boolean) -> Unit) {
        firestore.collection("board").document("${board.category}").collection("Contents").add(board)
            .addOnCompleteListener { task ->
                callback(task.isSuccessful)
            }
    }

    override fun updateBoard(): Flow<MutableList<Board>> = callbackFlow {
        val listenerRegistration = firestore.collection("board").document("Parenting").collection("Contents")
            .addSnapshotListener { snapshots, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val boardList = mutableListOf<Board>()
                snapshots?.documents?.forEach { document ->
                    val board = document.toObject(Board::class.java)
                    if (board != null) {
                        boardList.add(board)
                    }
                }
                trySend(boardList).isSuccess
            }

        awaitClose { listenerRegistration.remove() }
    }

    override fun getBoard() {
        TODO("Not yet implemented")
    }
}