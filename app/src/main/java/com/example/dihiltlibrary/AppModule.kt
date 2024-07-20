package com.example.dihiltlibrary

import com.example.dihiltlibrary.repository.ChatRepository
import com.example.dihiltlibrary.repository.ChatRepositoryImpl
import com.example.dihiltlibrary.repository.PostRepository
import com.example.dihiltlibrary.repository.PostRepositoryImpl
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
    fun provideFirebaseDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance()

    @Provides
    fun provideFirebaseRepository(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore,
        firebaseDatabase: FirebaseDatabase
    ): FirebaseRepository = FirebaseRepositoryImpl(firebaseAuth, firestore, firebaseDatabase)

    @Provides
    fun providePostRepository(
        firebaseDatabase: FirebaseDatabase,
        firestore: FirebaseFirestore
    ): PostRepository = PostRepositoryImpl(firebaseDatabase, firestore)

    @Provides
    fun provideChatRepository(
        firebaseDatabase: FirebaseDatabase
    ): ChatRepository = ChatRepositoryImpl(firebaseDatabase)
}