package com.example.model


data class Task(
    val taskId: String = "",
    val boardId: String = "",
    val title: String = "",
    val description: String = "",
    val status: String = "TO_DO", // Can be "TO_DO", "IN_PROGRESS", "DONE"
    val assigneeId: String? = null, // Nullable if unassigned
    val dueDate: Long? = null,
    val createdAt: Long = System.currentTimeMillis()
)