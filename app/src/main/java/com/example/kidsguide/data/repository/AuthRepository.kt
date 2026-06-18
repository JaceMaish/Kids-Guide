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
            
            // Fetch user role from Firestore
            val document = firestore.collection("users").document(uid).get().await()
            val role = document.getString("role") ?: "child"
            
            Result.success(User(uid, email, role))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        auth.signOut()
    }

    fun getCurrentUser(): User? {
        val firebaseUser = auth.currentUser
        return firebaseUser?.let {
            // Note: This won't have the role immediately without a fetch
            User(it.uid, it.email ?: "")
        }
    }
}
