package com.task.noteapp.domain.usecase.notes

import com.task.noteapp.domain.functional.Result
import com.task.noteapp.domain.repository.NoteRepository
import com.task.noteapp.domain.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteNote @Inject constructor(
    private val noteRepository: NoteRepository
) : FlowUseCase<Long, Long> {
    override fun invoke(params: Long?): Flow<Result<Long>> {
        return noteRepository.deleteNote(params!!)
    }
}
