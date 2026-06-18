package com.example.kidsguide.ui.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kidsguide.data.model.User
import com.example.kidsguide.data.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository = AuthRepository()) : ViewModel() {
    private val _authState = mutableStateOf<AuthState>(AuthState.Idle)
    val authState: State<AuthState> = _authState

    private var cachedUser: User? = null

    init {
        checkUserSession()
    }

    private fun checkUserSession() {
        val currentUser = repository.getCurrentUser()
        if (currentUser != null) {
            viewModelScope.launch {
                val role = repository.getUserRole(currentUser.uid)
                cachedUser = currentUser.copy(role = role)
            }
        }
    }

    fun tryAutoLogin(selectedRole: String): Boolean {
        val user = cachedUser
        return if (user != null && user.role == selectedRole) {
            _authState.value = AuthState.Success(user)
            true
        } else {
            false
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = repository.login(email, password)
            result.onSuccess { user ->
                _authState.value = AuthState.Success(user)
            }.onFailure { error ->
                _authState.value = AuthState.Error(error.message ?: "Login failed")
            }
        }
    }

    fun register(email: String, password: String, role: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = repository.register(email, password, role)
            result.onSuccess { user ->
                _authState.value = AuthState.Success(user)
            }.onFailure { error ->
                _authState.value = AuthState.Error(error.message ?: "Registration failed")
            }
        }
    }

    fun logout() {
        repository.logout()
        _authState.value = AuthState.Idle
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}
