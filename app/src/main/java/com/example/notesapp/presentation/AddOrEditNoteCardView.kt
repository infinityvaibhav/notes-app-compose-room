package com.example.notesapp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AddOrEditNoteCardView(
    title: String,
    note: String,
    isEditing:  Boolean,
    onTitleChanged: (String) -> Unit,
    onNoteChanged: (String) -> Unit,
    onSubmit: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Title
            OutlinedTextField(
                value = title,
                onValueChange = onTitleChanged,
                label = { Text("Title") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
            )

            // Note description
            OutlinedTextField(
                value = note,
                onValueChange = onNoteChanged,
                label = { Text("Note") },
                modifier = Modifier
                    .fillMaxWidth()
            )

            // Submit button
            Button(
                onClick = onSubmit,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(if (isEditing) "Save changes" else "Add")
            }
        }
    }
}

@Preview
@Composable
private fun AddOrEditNoteCardViewPreview() {
    AddOrEditNoteCardView(
        title = "Title",
        note = "Notes",
        isEditing = false,
        onTitleChanged = {},
        onNoteChanged = {},
        onSubmit = { }
    )
}