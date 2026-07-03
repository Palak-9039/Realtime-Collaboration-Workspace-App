package com.example.network

import com.example.model.Board
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseBoardRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    fun streamBoardDetails(boardId : String) : Flow<Board?> = callbackFlow {
        val docRef = firestore.collection("boards").document(boardId)

        val registration = docRef.addSnapshotListener { snapshot, error ->
            if(error != null){
                close(error)
                return@addSnapshotListener
            }

            if(snapshot != null && snapshot.exists()){
                val board = snapshot.toObject(Board::class.java)
                trySend(board)
            }else{
                trySend(null)
            }
        }

        awaitClose {
            registration.remove()
        }
    }

    suspend fun updateBoardState(board: Board){
        try{
            firestore.collection("boards").document(board.id).set(board).await()
        }catch (e : Exception){
            e.printStackTrace()
            throw e
        }
    }
}

