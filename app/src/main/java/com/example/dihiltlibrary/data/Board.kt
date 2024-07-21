package com.example.dihiltlibrary.data

data class Board(
    val id: String? = null,
    val title: String = "",
    val content: BoardContent? = null,
    val timestamp: String = "",
)
data class BoardContent(
    val text: String = "",
    val image: String = "",
)
