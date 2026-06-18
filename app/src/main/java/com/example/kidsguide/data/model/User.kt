package com.example.kidsguide.data.model

data class User(
    val uid: String = "",
    val email: String = "",
    val role: String = "child" // "child" or "parent"
)
