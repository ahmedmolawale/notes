package com.task.noteapp.domain.repository

import com.task.noteapp.domain.model.Note

interface NoteDetailRepository {
    suspend fun fetchNote(noteId: String): Note
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(noteId: String)
    suspend fun saveNote(note: Note)
}