package com.example.notesapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.notesapp.data.Note

class NotesViewModel {
    var notes by mutableStateOf(Note.mockNotes)

    var title by mutableStateOf("")
    var note by mutableStateOf("")
    var editingNoteId by mutableStateOf<Int?>(null)
    var sortBy by mutableStateOf("Date") //Sort by date or title
    var searchQuery by mutableStateOf("")
    var ascending by mutableStateOf(true)

    // CRUD
    fun addNote() {

    }

    fun updateNote() {

    }

    fun deleteNote(note: Note) {

    }

    fun editNote(note: Note) {

    }

    fun clearInputs() {
        title = ""
        note = ""
    }

    // Filtering and sorting notes
    val filteredAndSortedNotes: List<Note>
        get() = notes
}