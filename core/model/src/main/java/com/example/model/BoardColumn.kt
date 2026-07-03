package com.example.model

data class BoardColumn(
    val id: String = "",
    val name: String = "", // e.g., "To Do", "In Progress", "Done"
    val position: Int = 0,
    val tasks: List<Task> = emptyList()
)