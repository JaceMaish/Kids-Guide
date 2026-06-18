package com.example.kidsguide.ui.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kidsguide.data.model.QuizResult
import com.example.kidsguide.data.repository.AuthRepository
import kotlinx.coroutines.launch

class QuizViewModel(private val repository: AuthRepository = AuthRepository()) : ViewModel() {
    private val _quizResults = mutableStateOf<List<QuizResult>>(emptyList())
    val quizResults: State<List<QuizResult>> = _quizResults

    fun saveResult(lessonId: String, score: Int, total: Int) {
        val currentUser = repository.getCurrentUser() ?: return
        val result = QuizResult(
            userId = currentUser.uid,
            lessonId = lessonId,
            score = score,
            totalQuestions = total
        )
        viewModelScope.launch {
            repository.saveQuizResult(result)
        }
    }

    fun fetchAllResults() {
        viewModelScope.launch {
            _quizResults.value = repository.getChildResults()
        }
    }
}
