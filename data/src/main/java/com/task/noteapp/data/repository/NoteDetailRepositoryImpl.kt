package com.task.noteapp.data.repository

import com.task.noteapp.data.contract.cache.NoteCache
import com.task.noteapp.data.mapper.NoteEntityMapper
import com.task.noteapp.data.model.NoteEntity
import com.task.noteapp.domain.model.Note
import com.task.noteapp.domain.repository.NoteDetailRepository
import javax.inject.Inject

class NoteDetailRepositoryImpl @Inject constructor(
    private val noteCache: NoteCache,
    private val noteEntityMapper: NoteEntityMapper
) : NoteDetailRepository {

    override suspend fun updateNote(note: Note) {
        val noteEntity: NoteEntity = noteEntityMapper.mapToEntity(note)
        noteCache.updateNote(noteEntity)
    }

    override suspend fun deleteNote(noteId: Long) {
        noteCache.deleteNote(noteId)
    }

    override suspend fun saveNote(note: Note) {
        val noteEntity: NoteEntity = noteEntityMapper.mapToEntity(note)
        noteCache.saveNote(noteEntity)
    }
}