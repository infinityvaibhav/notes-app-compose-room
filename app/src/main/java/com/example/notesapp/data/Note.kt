package com.example.notesapp.data

import java.time.LocalDateTime

data class Note(
    val id: Int,
    val title: String,
    val note: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val isPinned: Boolean = false
) {
    companion object {
        val mockNotes = listOf(
            Note(
                id = 1,
                title = "Grocery List",
                note = "Buy milk, eggs, bread, butter, and coffee beans.",
                isPinned = true
            ),
            Note(
                id = 2,
                title = "Meeting Notes",
                note = "Discussed Q3 goals, sprint planning, and feature prioritization.",
                isPinned = true
            ),
            Note(
                id = 3,
                title = "App Ideas",
                note = "1. Dark mode support\n2. Export notes to PDF\n3. Tag-based filtering"
            ),
            Note(
                id = 4,
                title = "Workout Routine",
                note = "Monday: Upper Body\nWednesday: Lower Body\nFriday: Cardio & Core"
            ),
            Note(
                id = 5,
                title = "Book Recommendations",
                note = "1. Clean Code\n2. Pragmatic Programmer\n3. Refactoring"
            )
        )
    }
}
