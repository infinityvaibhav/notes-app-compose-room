package com.example.notesapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note ORDER BY createdAt DESC")
    suspend fun getAllNotesByDateDesc(): List<Note>

    @Query("SELECT * FROM Note ORDER BY createdAt ASC")
    suspend fun getAllNotesByDateAsc(): List<Note>

    @Query("SELECT * FROM Note ORDER BY title DESC")
    suspend fun getAllNotesByTitleDesc(): List<Note>

    @Query("SELECT * FROM Note ORDER BY title ASC")
    suspend fun getAllNotesByTitleAsc(): List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)
}