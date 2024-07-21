package com.example.dihiltlibrary.repository

import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class BoardRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): BoardRepository {
    override fun writeBoard() {
        TODO("Not yet implemented")
    }

    override fun updateBoard() {
        TODO("Not yet implemented")
    }

    override fun getBoard() {
        TODO("Not yet implemented")
    }
}