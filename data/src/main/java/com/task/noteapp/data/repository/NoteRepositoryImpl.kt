package com.task.noteapp.data.repository

import com.task.noteapp.domain.model.Note
import com.task.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor() : NoteRepository {
    override suspend fun getNotes(): Flow<List<Note>> {
    }
}