package com.task.noteapp.data.fakes

import com.task.noteapp.data.contract.cache.NoteCache
import com.task.noteapp.data.model.NoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeNoteCache : NoteCache {

    private val cache = LinkedHashMap<Long, NoteEntity>()
    override suspend fun saveNote(noteEntity: NoteEntity): Long {
        cache[noteEntity.id] = noteEntity
        return noteEntity.id
    }

    override fun fetchNotes(): Flow<List<NoteEntity>> {
        return flow {
            emit(cache.values.toList())
        }
    }

    override suspend fun updateNote(noteEntity: NoteEntity): Int {
        if (!cache.containsKey(noteEntity.id)) return 0
        val noteInCache = cache[noteEntity.id]
        val updatedNote = noteInCache?.copy(
            title = noteEntity.title, description = noteEntity.description, edited = true,
            imageUrl = noteEntity.imageUrl
        )
        cache[noteEntity.id] = updatedNote!!
        return 1
    }

    override suspend fun deleteNote(noteId: Long): Int {
        return if (cache.containsKey(noteId)) {
            cache.remove(noteId)
            1
        } else 0

    }

    override suspend fun fetchNote(noteId: Long): NoteEntity? {
        return cache[noteId]
    }
}