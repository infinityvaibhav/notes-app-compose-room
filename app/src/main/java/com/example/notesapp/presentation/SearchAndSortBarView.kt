package com.example.notesapp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchAndSortBarView(
    searchQuery: String,
    onSearchChanged: (String) -> Unit,
    sortBy: String,
    onSortByChanged: (String) -> Unit,
    ascending: Boolean,
    onOrderChanged: (Boolean) -> Unit
) {
    Column {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChanged,
            label = { Text("Search Notes")},
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search Icon")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Sort by")
            AssistChip(
                onClick = { onSortByChanged(if (sortBy == "Date") "Title" else "Date") },
                label = { Text("Sort by") },
                leadingIcon = { Icon(Icons.AutoMirrored.Filled.Sort, contentDescription = "sort by icon")}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchAndSortBarPreview() {
    SearchAndSortBarView(
        searchQuery = "",
        onSearchChanged = {},
        sortBy = "Date",
        onSortByChanged = {},
        ascending = false
    ) { }
}