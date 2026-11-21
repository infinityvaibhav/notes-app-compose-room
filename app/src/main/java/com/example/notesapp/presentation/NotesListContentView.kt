package com.example.notesapp.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NoteAdd
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notesapp.data.Note
import java.text.DateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun NotesListContentView(
    notes: List<Note>,
    onNoteClick: (Note) -> Unit,
    onDeleteNote: (Note) -> Unit
) {
    if (notes.isEmpty()) {
        EmptyNotesView()
    } else {
        NotesListView(
            notes = notes,
            onNoteClick = onNoteClick,
            onDeleteNote = onDeleteNote
        )
    }
}

@Composable
fun NotesListView(notes: List<Note>, onNoteClick: (Note) -> Unit, onDeleteNote: (Note) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(notes, key = { it.id}) { note ->
            NotesRowView(note, onNoteClick, onDeleteNote)
        }
    }
}

@Composable
fun NotesRowView(note: Note, onNoteClick: (Note) -> Unit, onDeleteNote: (Note) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val formattedDate  = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.getDefault())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNoteClick(note) },
        elevation = CardDefaults.cardElevation(3.dp)
    ) { }
}

@Preview(showBackground = true)
@Composable
private fun NotesListContentViewPreview() {
    NotesListContentView(
        notes = Note.mockNotes,
        onNoteClick = { },
        onDeleteNote = { }
    )
}
