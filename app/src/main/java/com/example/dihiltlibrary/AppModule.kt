package com.example.dihiltlibrary

import com.example.dihiltlibrary.repository.BoardRepository
import com.example.dihiltlibrary.repository.BoardRepositoryImpl
import com.example.dihiltlibrary.repository.FirebaseRepository
import com.example.dihiltlibrary.repository.FirebaseRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
    @Provides
    fun provideFirebaseFireStore(): FirebaseFirestore = FirebaseFirestore.getInstance()
    @Provides
    fun provideFirebaseRepository(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): FirebaseRepository = FirebaseRepositoryImpl(firebaseAuth, firestore)

    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance()
    @Provides
    fun provideBoardRepository(
        firebaseDatabase: FirebaseDatabase
    ): BoardRepository = BoardRepositoryImpl(firebaseDatabase)
}