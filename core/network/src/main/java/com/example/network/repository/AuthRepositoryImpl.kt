package com.example.network.repository

import com.example.model.Resource
import com.example.model.User
import com.example.network.AuthRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl  @Inject constructor(
    private val firebaseAuth : FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository{

    val userCollection = firestore.collection("users")

    override val currentUserState: Flow<Resource<User?>> = callbackFlow {
        trySend(Resource.Loading)

        val authStateListener = FirebaseAuth.AuthStateListener{ auth ->
            val firebaseUser = firebaseAuth.currentUser

            if(firebaseUser == null){
                trySend(Resource.Success(null))
            }else{
                // If a user is logged in, fetch their profile document from Firestore
                userCollection.document(firebaseUser.uid).get()
                    .addOnSuccessListener { documentSnapshot ->
                        val user = documentSnapshot.toObject(User::class.java)
                        trySend(Resource.Success(user))
                    }
                    .addOnFailureListener { exception ->
                        trySend(Resource.Error(exception,"Failed to sync user profile data"))
                    }
            }
        }

        firebaseAuth.addAuthStateListener(authStateListener)

        awaitClose { firebaseAuth.removeAuthStateListener(authStateListener) }
    }

    override fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }

    override suspend fun signInWithEmail(
        email: String,
        password: String
    ): Flow<Resource<User>> = flow{
        emit(Resource.Loading)

        try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email,password).await()
            val uid = authResult.user?.uid ?: throw Exception("User identification failed during login")

            val userDocument = userCollection.document(uid).get().await()
            val user = userDocument.toObject(User::class.java)
                ?: throw Exception("User profile details not found in database")

            emit(Resource.Success(user))
        }catch (e : Exception){
            emit(Resource.Error(e, e.localizedMessage ?: "An error occurred during sign in "))
        }
    }

    override suspend fun signUpWithEmail(
        name: String,
        email: String,
        password: String,
        department: String
    ): Flow<Resource<User>> = flow{
        emit(Resource.Loading)
        try{
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email,password).await()
            val uid = authResult.user?.uid ?: throw Exception("Account creation failed")

            val newUser = User(
                userId = uid,
                fullName = name,
                email = email,
                department = department,
                profileImageUrl = null
            )

            userCollection.document(uid).set(newUser).await()

            emit(Resource.Success(newUser))
        }catch (e : Exception){
            emit(Resource.Error(e, e.localizedMessage ?: "An error occurred during account creation"))
        }
    }

    override suspend fun signOut(): Flow<Resource<Unit>> = flow{
        emit(Resource.Loading)
        try {
            firebaseAuth.signOut()
            emit(Resource.Success(Unit))
        }catch (e : Exception){
            emit(Resource.Error(e, e.localizedMessage ?: "Failed to sign out cleanly"))
        }
    }


}