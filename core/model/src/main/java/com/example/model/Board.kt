package com.example.model

data class Board(
    val id: String = "",
    val title: String = "",
    val columns: List<BoardColumn> = emptyList(),
    val members: List<String> = emptyList()
)