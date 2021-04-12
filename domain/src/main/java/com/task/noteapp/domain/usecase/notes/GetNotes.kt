package com.task.noteapp.domain.usecase.notes

import com.task.noteapp.domain.functional.Result
import com.task.noteapp.domain.model.Note
import com.task.noteapp.domain.repository.NoteRepository
import com.task.noteapp.domain.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotes @Inject constructor(
    private val noteRepository: NoteRepository
) : FlowUseCase<Unit, List<Note>> {
    override fun invoke(params: Unit?): Flow<Result<List<Note>>> {
        return noteRepository.getNotes()
    }
}