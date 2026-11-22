package com.example.notesapp.presentation

import com.example.notesapp.data.Note
import com.example.notesapp.data.NoteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.time.LocalDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}

class FakeNoteDao(
    initialNotes: List<Note> = emptyList()
) : NoteDao {
    private val notesList = initialNotes.toMutableList()

    override suspend fun getAllNotesByDateDesc(): List<Note> {
        return notesList.sortedByDescending { it.createdAt }
    }

    override suspend fun getAllNotesByDateAsc(): List<Note> {
        return notesList.sortedBy { it.createdAt }
    }

    override suspend fun getAllNotesByTitleDesc(): List<Note> {
        return notesList.sortedByDescending { it.title.lowercase() }
    }

    override suspend fun getAllNotesByTitleAsc(): List<Note> {
        return notesList.sortedBy { it.title.lowercase() }
    }

    override suspend fun insert(note: Note) {
        notesList.removeAll { it.id == note.id }
        notesList.add(note)
    }

    override suspend fun update(note: Note) {
        val index = notesList.indexOfFirst { it.id == note.id }
        if (index != -1) {
            notesList[index] = note
        }
    }

    override suspend fun delete(note: Note) {
        notesList.removeAll { it.id == note.id }
    }
}

class NotesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var fakeNoteDao: FakeNoteDao
    private lateinit var viewModel: NotesViewModel

    private val sampleNote1 = Note(
        id = 1,
        title = "Banana Note",
        note = "Buy bananas",
        createdAt = LocalDateTime.of(2025, 1, 1, 10, 0),
        updatedAt = LocalDateTime.of(2025, 1, 1, 10, 0)
    )

    private val sampleNote2 = Note(
        id = 2,
        title = "Apple Note",
        note = "Buy apples",
        createdAt = LocalDateTime.of(2025, 1, 2, 12, 0),
        updatedAt = LocalDateTime.of(2025, 1, 2, 12, 0)
    )

    private val sampleNote3 = Note(
        id = 3,
        title = "Cherry Note",
        note = "Bake cherry pie",
        createdAt = LocalDateTime.of(2025, 1, 3, 15, 0),
        updatedAt = LocalDateTime.of(2025, 1, 3, 15, 0)
    )

    @Before
    fun setUp() {
        fakeNoteDao = FakeNoteDao(listOf(sampleNote1, sampleNote2, sampleNote3))
        viewModel = NotesViewModel(fakeNoteDao)
    }

    @Test
    fun `initial state loads notes sorted by date ascending`() {
        val expected = listOf(sampleNote1, sampleNote2, sampleNote3)
        assertEquals(expected, viewModel.notes)
        assertEquals("", viewModel.title)
        assertEquals("", viewModel.note)
        assertNull(viewModel.editingNoteId)
        assertEquals("Date", viewModel.sortBy)
        assertEquals("", viewModel.searchQuery)
        assertTrue(viewModel.ascending)
    }

    @Test
    fun `addNote with valid content inserts note and clears inputs`() = runTest {
        viewModel.title = "New Title"
        viewModel.note = "New Content"

        viewModel.addNote()

        assertEquals("", viewModel.title)
        assertEquals("", viewModel.note)
        assertNull(viewModel.editingNoteId)
        assertEquals(4, viewModel.notes.size)
        assertTrue(viewModel.notes.any { (it.title == "New Title") && (it.note == "New Content") })
    }

    @Test
    fun `addNote with blank note does not insert note or clear inputs`() = runTest {
        viewModel.title = "Title without content"
        viewModel.note = "   "

        viewModel.addNote()

        assertEquals("Title without content", viewModel.title)
        assertEquals("   ", viewModel.note)
        assertEquals(3, viewModel.notes.size)
    }

    @Test
    fun `editNote populates input fields and editingNoteId`() {
        viewModel.editNote(sampleNote2)

        assertEquals(sampleNote2.id, viewModel.editingNoteId)
        assertEquals(sampleNote2.title, viewModel.title)
        assertEquals(sampleNote2.note, viewModel.note)
    }

    @Test
    fun `updateNote updates existing note and clears inputs`() = runTest {
        viewModel.editNote(sampleNote1)
        viewModel.title = "Updated Banana Title"
        viewModel.note = "Updated Banana Content"

        viewModel.updateNote()

        assertNull(viewModel.editingNoteId)
        assertEquals("", viewModel.title)
        assertEquals("", viewModel.note)

        val updated = viewModel.notes.find { it.id == sampleNote1.id }
        assertEquals("Updated Banana Title", updated?.title)
        assertEquals("Updated Banana Content", updated?.note)
    }

    @Test
    fun `updateNote does nothing when editingNoteId is null`() = runTest {
        viewModel.title = "Some Title"
        viewModel.note = "Some Content"

        viewModel.updateNote()

        assertEquals(3, viewModel.notes.size)
        assertEquals("Some Title", viewModel.title)
        assertEquals("Some Content", viewModel.note)
    }

    @Test
    fun `updateNote does nothing when editingNoteId does not match existing note`() = runTest {
        viewModel.editingNoteId = 999
        viewModel.title = "Non-existent Note"
        viewModel.note = "Content"

        viewModel.updateNote()

        assertEquals(999, viewModel.editingNoteId)
        assertEquals(3, viewModel.notes.size)
    }

    @Test
    fun `deleteNote removes note from database and reloads notes`() = runTest {
        viewModel.deleteNote(sampleNote2)

        assertEquals(2, viewModel.notes.size)
        assertTrue(viewModel.notes.none { it.id == sampleNote2.id })
    }

    @Test
    fun `clearInputs resets title note and editingNoteId`() {
        viewModel.editNote(sampleNote1)
        viewModel.clearInputs()

        assertEquals("", viewModel.title)
        assertEquals("", viewModel.note)
        assertNull(viewModel.editingNoteId)
    }

    @Test
    fun `filteredAndSortedNotes filters notes by searchQuery matching title`() {
        viewModel.searchQuery = "Banana"

        val filtered = viewModel.filteredAndSortedNotes
        assertEquals(1, filtered.size)
        assertEquals(sampleNote1, filtered.first())
    }

    @Test
    fun `filteredAndSortedNotes filters notes by searchQuery matching content case-insensitively`() {
        viewModel.searchQuery = "bAkE"

        val filtered = viewModel.filteredAndSortedNotes
        assertEquals(1, filtered.size)
        assertEquals(sampleNote3, filtered.first())
    }

    @Test
    fun `filteredAndSortedNotes returns empty list when searchQuery has no matches`() {
        viewModel.searchQuery = "NonExistentKeyword"

        assertTrue(viewModel.filteredAndSortedNotes.isEmpty())
    }

    @Test
    fun `filteredAndSortedNotes sorts by Title ascending`() {
        viewModel.sortBy = "Title"
        viewModel.ascending = true

        val sorted = viewModel.filteredAndSortedNotes
        assertEquals(listOf(sampleNote2, sampleNote1, sampleNote3), sorted)
    }

    @Test
    fun `filteredAndSortedNotes sorts by Title descending`() {
        viewModel.sortBy = "Title"
        viewModel.ascending = false

        val sorted = viewModel.filteredAndSortedNotes
        assertEquals(listOf(sampleNote3, sampleNote1, sampleNote2), sorted)
    }

    @Test
    fun `filteredAndSortedNotes sorts by Date ascending`() {
        viewModel.sortBy = "Date"
        viewModel.ascending = true

        val sorted = viewModel.filteredAndSortedNotes
        assertEquals(listOf(sampleNote1, sampleNote2, sampleNote3), sorted)
    }

    @Test
    fun `filteredAndSortedNotes sorts by Date descending`() {
        viewModel.sortBy = "Date"
        viewModel.ascending = false

        val sorted = viewModel.filteredAndSortedNotes
        assertEquals(listOf(sampleNote3, sampleNote2, sampleNote1), sorted)
    }
}
