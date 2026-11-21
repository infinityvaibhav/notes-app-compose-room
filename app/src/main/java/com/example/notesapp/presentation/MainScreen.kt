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
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: NotesViewModel = viewModel()
) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp)
    ) {
        NotesHeaderView()

        Spacer(Modifier.height(8.dp))

        SearchAndSortBarView()

        Spacer(Modifier.height(12.dp))

        AddOrEditNoteCardView()

        HorizontalDivider(Modifier.padding(vertical = 12.dp))

        NotesListOrEmptyView()
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val viewModel = remember { NotesViewModel() }
    MainScreen(viewModel = viewModel)
}