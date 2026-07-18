package com.example.auth.domain.repository

import com.example.model.Resource
import com.example.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    // Streams the real-time authentication status of the user
    val currentUserState: Flow<Resource<User?>>

    // Session status check
    fun getCurrentUserId(): String?

    // Auth operations wrapping the user state or success confirmations
    suspend fun signInWithEmail(email: String, password: String): Flow<Resource<User>>

    suspend fun signUpWithEmail(
        name: String,
        email: String,
        password: String,
        department: String
    ): Flow<Resource<User>>

    suspend fun signOut(): Flow<Resource<Unit>>
}