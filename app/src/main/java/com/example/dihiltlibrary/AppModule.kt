package com.example.dihiltlibrary

import android.content.Context
import com.example.dihiltlibrary.repository.BoardRepository
import com.example.dihiltlibrary.repository.BoardRepositoryImpl
import com.example.dihiltlibrary.repository.ChatRepository
import com.example.dihiltlibrary.repository.ChatRepositoryImpl
import com.example.dihiltlibrary.repository.PostRepository
import com.example.dihiltlibrary.repository.PostRepositoryImpl
import com.example.dihiltlibrary.repository.FirebaseRepository
import com.example.dihiltlibrary.repository.FirebaseRepositoryImpl
import com.example.dihiltlibrary.repository.GroupRepository
import com.example.dihiltlibrary.repository.GroupRepositoryImpl
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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

    @Provides
    fun provideBoardRepository(
        firestore: FirebaseFirestore
    ): BoardRepository = BoardRepositoryImpl(firestore)

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }
    @Provides
    fun provideGroupRepository(
        fusedLocationClient: FusedLocationProviderClient,
        @ApplicationContext
        context: Context
    ): GroupRepository = GroupRepositoryImpl(context, fusedLocationClient)
}