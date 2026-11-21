package com.example.notesapp.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Text("Main Screen")
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}