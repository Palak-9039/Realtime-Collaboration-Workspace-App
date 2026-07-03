package com.example.model

data class Task(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val assignedTo: List<String> = emptyList(),
    val position: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)
