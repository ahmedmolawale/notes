package com.task.noteapp.domain.repository

import com.task.noteapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun getNotes(): Flow<List<Note>>
}