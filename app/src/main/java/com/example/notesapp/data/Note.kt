package com.example.notesapp.data

import java.time.LocalDateTime

data class Note(
    val id: Int,
    val title: String,
    val note: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val isPinned: Boolean = false
)
