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
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notesapp.R

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
            label = { Text(stringResource(R.string.search_notes)) },
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = stringResource(R.string.cd_search_icon))
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(stringResource(R.string.sort_by), color = MaterialTheme.colorScheme.onSurfaceVariant)
                AssistChip(
                    onClick = { onSortByChanged(if (sortBy == "Date") "Title" else "Date") },
                    label = { Text(if (sortBy == "Date") stringResource(R.string.sort_by_date) else stringResource(R.string.sort_by_title)) },
                    leadingIcon = { Icon(Icons.AutoMirrored.Filled.Sort, contentDescription = stringResource(R.string.cd_sort_by_icon)) }
                )
            }

            AssistChip(
                onClick = { onOrderChanged(!ascending) },
                label = { Text(if (ascending) stringResource(R.string.ascending) else stringResource(R.string.descending)) },
                leadingIcon = {
                    Icon(
                        imageVector = if (ascending) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                        contentDescription = stringResource(R.string.cd_sort_by_icon)
                    )
                }
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
        sortBy = "Title",
        onSortByChanged = {},
        ascending = false
    ) { }
}