package com.task.noteapp.features.notes.fakes

import com.task.noteapp.domain.exception.Failure
import com.task.noteapp.domain.functional.Result
import com.task.noteapp.domain.model.Note
import com.task.noteapp.domain.repository.NoteRepository
import com.task.noteapp.features.notes.data.DummyData
import com.task.noteapp.features.utils.ResponseType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeNoteRepository : NoteRepository {

    private var notesFlow: Flow<Result<List<Note>>> = flowOf(Result.Success(DummyData.noteList))
    private var noteFlow: Flow<Result<Note>> = flowOf(Result.Success(DummyData.note))
    private var longFlow: Flow<Result<Long>> = flowOf(Result.Success(1))

    var noteListResponseType: ResponseType = ResponseType.DATA
        set(value) {
            field = value
            notesFlow = makeResponse(value)
        }

    var noteResponseType: ResponseType = ResponseType.DATA
        set(value) {
            field = value
            noteFlow = makeNoteResponse(value)
        }

    var longResponseType: ResponseType = ResponseType.DATA
        set(value) {
            field = value
            longFlow = makeLongResponse(value)
        }

    private fun makeResponse(type: ResponseType): Flow<Result<List<Note>>> {
        return when (type) {
            ResponseType.DATA -> notesFlow
            ResponseType.EMPTY_DATA -> flowOf(Result.Success(listOf()))
            else -> {
                flowOf(Result.Error(Failure.NoteError))
            }
        }
    }

    private fun makeNoteResponse(type: ResponseType): Flow<Result<Note>> {
        return when (type) {
            ResponseType.DATA -> noteFlow
            ResponseType.RESOURCE_NOT_FOUND -> flowOf(Result.Error(Failure.NoteNotFound))
            else -> {
                flowOf(Result.Error(Failure.NoteError))
            }
        }
    }

    private fun makeLongResponse(type: ResponseType): Flow<Result<Long>> {
        return when (type) {
            ResponseType.DATA -> longFlow
            ResponseType.RESOURCE_NOT_FOUND -> flowOf(Result.Error(Failure.NoteNotFound))
            else -> {
                flowOf(Result.Error(Failure.NoteError))
            }
        }
    }


    override fun getNotes(): Flow<Result<List<Note>>> {
        return notesFlow
    }

    override fun updateNote(note: Note): Flow<Result<Note>> {
        return noteFlow
    }

    override fun fetchNote(noteId: Long): Flow<Result<Note>> {
        return noteFlow
    }

    override fun deleteNote(noteId: Long): Flow<Result<Long>> {
        return longFlow
    }

    override fun saveNote(note: Note): Flow<Result<Note>> {
        return noteFlow
    }
}