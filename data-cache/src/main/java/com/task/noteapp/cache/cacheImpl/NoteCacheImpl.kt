package com.task.noteapp.cache.cacheImpl

import com.task.noteapp.cache.entity.NoteCacheModel
import com.task.noteapp.cache.mapper.NoteCacheModelMapper
import com.task.noteapp.cache.room.NotesDao
import com.task.noteapp.data.contract.cache.NoteCache
import com.task.noteapp.data.model.NoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class NoteCacheImpl @Inject constructor(
    private val notesDao: NotesDao,
    private val noteCacheModelMapper: NoteCacheModelMapper
) : NoteCache {


    override suspend fun saveNote(noteEntity: NoteEntity) {
        val noteCacheModel: NoteCacheModel = noteCacheModelMapper.mapToModel(noteEntity)
        noteCacheModel.createdAt = Date()
        noteCacheModel.edited = false
        notesDao.insertNote(noteCacheModel)
    }

    override fun fetchNotes(): Flow<List<NoteEntity>> {
        return notesDao.fetchNotes().map {
            it.map(noteCacheModelMapper::mapToEntity)
        }
    }

    override suspend fun updateNote(noteEntity: NoteEntity) {
        val noteCacheModel: NoteCacheModel = noteCacheModelMapper.mapToModel(noteEntity)
        noteCacheModel.edited = true
        notesDao.updateANote(noteCacheModel)
    }

    override suspend fun deleteNote(noteId: Long) {
        notesDao.deleteANote(noteId)
    }
}