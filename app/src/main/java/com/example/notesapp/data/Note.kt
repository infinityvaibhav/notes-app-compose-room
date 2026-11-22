package com.example.notesapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity(tableName = "Note")
@TypeConverters(Converters::class)
data class Note @OptIn(ExperimentalUuidApi::class) constructor(
    @PrimaryKey val id: Int = Uuid.random().hashCode(),
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

object Converters {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    @JvmStatic
    fun fromLocalDateTime(value: LocalDateTime?): String? {
        return value?.format(formatter)
    }

    @TypeConverter
    @JvmStatic
    fun toLocalDateTime(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it, formatter) }
    }
}