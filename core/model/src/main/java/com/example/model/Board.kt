package com.example.model

import java.security.Timestamp

data class Board(
    val boardId: String = "",
    val title: String = "",
    val description: String = "",
    val creatorId: String = "",
    val collaborators: List<String> = emptyList(), // List of User UIDs
    val createdAt: Long = System.currentTimeMillis()
)