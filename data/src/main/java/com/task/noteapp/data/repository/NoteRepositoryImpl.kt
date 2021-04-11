package com.task.noteapp.data.repository

import com.task.noteapp.data.contract.cache.NoteCache
import com.task.noteapp.data.mapper.NoteEntityMapper
import com.task.noteapp.domain.model.Note
import com.task.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteCache: NoteCache,
    private val noteEntityMapper: NoteEntityMapper
) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return noteCache.fetchNotes().map(noteEntityMapper::mapToDomainList)
    }
}