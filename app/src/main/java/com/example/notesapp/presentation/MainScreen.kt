package com.example.notesapp.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: NotesViewModel
) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp)
    ) {
        NotesHeaderView()

        Spacer(Modifier.height(8.dp))

        SearchAndSortBarView(
            searchQuery = viewModel.searchQuery,
            onSearchChanged = { viewModel.searchQuery = it },
            sortBy = viewModel.sortBy,
            onSortByChanged = { viewModel.sortBy = it },
            ascending = viewModel.ascending,
            onOrderChanged = { viewModel.ascending = it }
        )

        Spacer(Modifier.height(12.dp))

        AddOrEditNoteCardView(
            title = viewModel.title,
            note = viewModel.note,
            isEditing = viewModel.editingNoteId != null,
            onTitleChanged = { viewModel.title = it },
            onNoteChanged = { viewModel.note = it },
            onSubmit = {
                if (viewModel.editingNoteId == null) { viewModel.addNote() } else viewModel.updateNote()
            }
        )

        HorizontalDivider(Modifier.padding(vertical = 12.dp))

        NotesListContentView(
            notes = viewModel.filteredAndSortedNotes,
            onNoteClick = { viewModel.editNote(it) },
            onDeleteNote = {
                viewModel.deleteNote(it)
                viewModel.clearInputs()
            },
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val viewModel = remember { NotesViewModel() }
    MainScreen(viewModel = viewModel)
}