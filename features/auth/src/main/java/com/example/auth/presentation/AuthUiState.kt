package com.example.auth.presentation

data class AuthUiState(
    val isLoading: Boolean = false,
    val error: String? = null
)