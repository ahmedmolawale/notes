package com.task.noteapp.domain.usecase.notes

import com.task.noteapp.domain.functional.Result
import com.task.noteapp.domain.model.Note
import com.task.noteapp.domain.repository.NoteRepository
import com.task.noteapp.domain.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateNote @Inject constructor(
    private val noteRepository: NoteRepository
) : FlowUseCase<Note, Note> {
    override fun invoke(params: Note?): Flow<Result<Note>> {
        return noteRepository.updateNote(params!!)
    }
}