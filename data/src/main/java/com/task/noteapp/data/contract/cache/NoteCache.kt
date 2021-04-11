package com.task.noteapp.data.contract.cache

import com.task.noteapp.data.model.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteCache {
    suspend fun saveNote(noteEntity: NoteEntity)
    fun fetchNotes(): Flow<List<NoteEntity>>
    suspend fun updateNote(noteEntity: NoteEntity)
    suspend fun deleteNote(noteId: Long)
}