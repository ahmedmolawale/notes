package com.task.noteapp.domain.repository

import com.task.noteapp.domain.functional.Result
import com.task.noteapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(): Flow<Result<List<Note>>>
    fun updateNote(note: Note): Flow<Result<Note>>
    fun fetchNote(noteId: Long): Flow<Result<Note>>
    fun deleteNote(noteId: Long): Flow<Result<Long>>
    fun saveNote(note: Note): Flow<Result<Note>>
}