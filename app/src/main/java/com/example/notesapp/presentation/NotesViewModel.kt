package com.example.notesapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.Note
import com.example.notesapp.data.NoteDao
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class NotesViewModel(
    private val noteDao: NoteDao
): ViewModel() {
    var notes by mutableStateOf(listOf<Note>())
        private set

    var title by mutableStateOf("")
    var note by mutableStateOf("")
    var editingNoteId by mutableStateOf<Int?>(null)
    var sortBy by mutableStateOf("Date") //Sort by date or title
    var searchQuery by mutableStateOf("")
    var ascending by mutableStateOf(true)

    init {
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            notes = when(sortBy) {
                    "Title" -> if (ascending) noteDao.getAllNotesByTitleAsc() else noteDao.getAllNotesByTitleDesc()
                    else -> if (ascending) noteDao.getAllNotesByDateAsc() else noteDao.getAllNotesByDateDesc()
            }
        }
    }

    // CRUD
    fun addNote() {
        if (note.isBlank()) return

        val newNote = Note(
            title = title,
            note = note
        )

        // insert in room database
        viewModelScope.launch {
            noteDao.insert(newNote)
            loadNotes()
        }
        clearInputs()
    }

    fun updateNote() {
        if (editingNoteId == null) return

        val updatedNotes = notes.find { it.id == editingNoteId }?.copy(
            title = title,
            note = this.note,
            updatedAt = LocalDateTime.now()
        ) ?: return

        viewModelScope.launch {
            noteDao.update(updatedNotes)
            loadNotes()
        }

        editingNoteId = null
        clearInputs()
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteDao.delete(note)
            loadNotes()
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