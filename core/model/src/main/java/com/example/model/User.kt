package com.example.model


data class User(
    val userId: String = "",
    val fullName: String = "",
    val email: String = "",
    val department: String = "",
    val profileImageUrl: String? = null
)