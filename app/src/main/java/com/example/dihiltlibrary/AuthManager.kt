package com.example.dihiltlibrary

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthManager @Inject constructor() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    val currentUserId: String? get() = firebaseAuth.uid
}