package com.task.noteapp.domain.repository

import com.task.noteapp.domain.model.Note

interface NoteDetailRepository {
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(noteId: Long)
    suspend fun saveNote(note: Note)
}