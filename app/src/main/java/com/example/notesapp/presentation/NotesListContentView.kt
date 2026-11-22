package com.example.notesapp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notesapp.data.Note

@Composable
fun NotesListContentView(
    notes: List<Note>,
    onNoteClick: (Note) -> Unit,
    onDeleteNote: (Note) -> Unit,
    modifier: Modifier = Modifier
) {
    if (notes.isEmpty()) {
        EmptyNotesView(modifier = modifier)
    } else {
        NotesListView(
            notes = notes,
            onNoteClick = onNoteClick,
            onDeleteNote = onDeleteNote,
            modifier = modifier
        )
    }
}

@Composable
fun NotesListView(
    notes: List<Note>,
    onNoteClick: (Note) -> Unit,
    onDeleteNote: (Note) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(notes, key = { it.id }) { note ->
            NotesRowView(note, onNoteClick, onDeleteNote)
        }
    }
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
