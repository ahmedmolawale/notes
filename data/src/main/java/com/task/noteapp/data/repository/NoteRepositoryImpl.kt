package com.task.noteapp.data.repository

import com.task.noteapp.data.contract.cache.NoteCache
import com.task.noteapp.data.mapper.NoteEntityMapper
import com.task.noteapp.data.model.NoteEntity
import com.task.noteapp.domain.exception.Failure
import com.task.noteapp.domain.functional.Result
import com.task.noteapp.domain.model.Note
import com.task.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepositoryImpl @Inject constructor(
    private val noteCache: NoteCache,
    private val noteEntityMapper: NoteEntityMapper
) : NoteRepository {
    override fun getNotes(): Flow<Result<List<Note>>> {
        return noteCache.fetchNotes().map(noteEntityMapper::mapToDomainList)
            .map { Result.Success(it) }
    }

    override fun updateNote(note: Note): Flow<Result<Note>> {
        return flow {
            val noteEntity: NoteEntity = noteEntityMapper.mapToEntity(note)
            val res = noteCache.updateNote(noteEntity)
            if (res > 0) emit(Result.Success(note))
            else emit(Result.Error(Failure.NoteNotFound))
        }
    }

    override fun fetchNote(noteId: Long): Flow<Result<Note>> {
        return flow {
            val noteEntity = noteCache.fetchNote(noteId)
            noteEntity?.let {
                emit(Result.Success(noteEntityMapper.mapToDomain(it)))
            } ?: emit(Result.Error(Failure.NoteNotFound))
        }
    }

    override fun deleteNote(noteId: Long): Flow<Result<Long>> {
        return flow {
            val res = noteCache.deleteNote(noteId)
            if (res > 0) emit(Result.Success(noteId))
            else emit(Result.Error(Failure.NoteNotFound))
        }
    }

    override fun saveNote(note: Note): Flow<Result<Note>> {
        return flow {
            val noteEntity: NoteEntity = noteEntityMapper.mapToEntity(note)
            val res = noteCache.saveNote(noteEntity)
            if (res > 0) emit(Result.Success(note))
            else emit(Result.Error(Failure.NoteError))
        }
    }
}
