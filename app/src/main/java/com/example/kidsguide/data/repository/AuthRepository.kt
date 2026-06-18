package com.example.kidsguide.data.repository

import com.example.kidsguide.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun login(email: String, password: String): Result<User> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: throw Exception("User ID not found")
            
            val document = firestore.collection("users").document(uid).get().await()
            val role = document.getString("role") ?: "child"
            
            Result.success(User(uid, email, role))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(email: String, password: String, role: String): Result<User> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: throw Exception("User creation failed")
            
            val user = User(uid, email, role)
            firestore.collection("users").document(uid).set(user).await()
            
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserRole(uid: String): String {
        return try {
            val document = firestore.collection("users").document(uid).get().await()
            document.getString("role") ?: "child"
        } catch (e: Exception) {
            "child"
        }
    }

    fun logout() {
        auth.signOut()
    }

    fun getCurrentUser(): User? {
        val firebaseUser = auth.currentUser
        return firebaseUser?.let {
            User(it.uid, it.email ?: "")
        }
    }

    suspend fun saveQuizResult(result: com.example.kidsguide.data.model.QuizResult) {
        try {
            firestore.collection("quiz_results")
                .add(result)
                .await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getChildResults(): List<com.example.kidsguide.data.model.QuizResult> {
        return try {
            val snapshot = firestore.collection("quiz_results")
                .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get()
                .await()
            snapshot.toObjects(com.example.kidsguide.data.model.QuizResult::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
    // Real-time listener for quiz results
    fun observeQuizResults(onResult: (List<com.example.kidsguide.data.model.QuizResult>) -> Unit) {
        firestore.collection("quiz_results")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    e.printStackTrace()
                    onResult(emptyList())
                    return@addSnapshotListener
                }
                val results = snapshot?.toObjects(com.example.kidsguide.data.model.QuizResult::class.java) ?: emptyList()
                onResult(results)
            }
    }
}

