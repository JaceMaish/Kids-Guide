package com.example.kidsguide.data.model

data class QuizResult(
    val userId: String = "",
    val lessonId: String = "",
    val score: Int = 0,
    val totalQuestions: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)
