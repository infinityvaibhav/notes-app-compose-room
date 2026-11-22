package com.example.notesapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.notesapp.data.Note
import java.time.LocalDateTime

class NotesViewModel: ViewModel() {
    var notes by mutableStateOf(Note.mockNotes)

    var title by mutableStateOf("")
    var note by mutableStateOf("")
    var editingNoteId by mutableStateOf<Int?>(null)
    var sortBy by mutableStateOf("Date") //Sort by date or title
    var searchQuery by mutableStateOf("")
    var ascending by mutableStateOf(true)

    // CRUD
    fun addNote() {
        if (note.isBlank()) return

        val newNote = Note(
            title = title,
            note = note
        )

        notes += newNote
        clearInputs()
    }

    fun updateNote() {
        if (editingNoteId == null) return

        notes = notes.map { note ->
            if (note.id == editingNoteId) {
                note.copy(
                    title = title,
                    note = this.note,
                    updatedAt = LocalDateTime.now()
                )
            } else {
                note
            }
        }

        editingNoteId = null
        clearInputs()
    }

    fun deleteNote(note: Note) {
        notes = notes.filter { it.id != note.id }
        if (editingNoteId == note.id) {
            clearInputs()
        }
    }

    fun editNote(note: Note) {
        editingNoteId = note.id
        title = note.title
        this.note = note.note
    }

    fun clearInputs() {
        title = ""
        note = ""
        editingNoteId = null
    }

    // Filtering and sorting notes
    val filteredAndSortedNotes: List<Note>
        get() = notes
            .filter { it.title.contains(searchQuery, ignoreCase = true) || it.note.contains(searchQuery, ignoreCase = true)}
            .sortedWith(
                comparator = when(sortBy) {
                    "Title" -> compareBy<Note> { it.title.lowercase() }
                    else -> compareBy { it.createdAt }
                }.let { if (ascending) it else it.reversed() }
            )
}