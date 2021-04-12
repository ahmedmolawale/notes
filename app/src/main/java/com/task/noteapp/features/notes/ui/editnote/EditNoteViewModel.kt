package com.task.noteapp.features.notes.ui.editnote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.noteapp.R
import com.task.noteapp.domain.exception.Failure
import com.task.noteapp.domain.functional.Result
import com.task.noteapp.domain.model.Note
import com.task.noteapp.domain.usecase.notes.DeleteNote
import com.task.noteapp.domain.usecase.notes.FetchNote
import com.task.noteapp.domain.usecase.notes.UpdateNote
import com.task.noteapp.features.notes.mapper.NotePresentationMapper
import com.task.noteapp.features.notes.model.NotePresentation
import com.task.noteapp.features.notes.model.states.NoteEditView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val updateNote: UpdateNote,
    private val deleteNote: DeleteNote,
    private val fetchNote: FetchNote,
    private val notePresentationMapper: NotePresentationMapper
) : ViewModel() {

    private val _noteEditView = MutableLiveData<NoteEditView>()
    val noteEditView: LiveData<NoteEditView>
        get() = _noteEditView

    fun getNote(noteId: Long) {
        viewModelScope.launch {
            _noteEditView.postValue(NoteEditView(loading = true))
            fetchNote(noteId).collect {
                _noteEditView.postValue(NoteEditView(loading = false))
                when (it) {
                    is Result.Success -> {
                        handleNoteSuccess(it.data)
                    }
                    is Result.Error -> {
                        handleNoteEditError(it.failure)
                    }
                }
            }
        }
    }

    private fun handleNoteSuccess(note: Note) {
        _noteEditView.postValue(
            NoteEditView(
                note = notePresentationMapper.mapToPresentation(
                    note
                )
            )
        )
    }

    private fun handleNoteEditError(failure: Failure) {
        when (failure) {
            is Failure.NoteNotFound -> {
                _noteEditView.value = NoteEditView(message = R.string.note_not_exist)
            }
            else -> {
                _noteEditView.value = NoteEditView(message = R.string.error_on_note)
            }
        }
    }

    fun updateANote(notePresentation: NotePresentation) {
        viewModelScope.launch {
            _noteEditView.postValue(NoteEditView(loading = true))
            val note = notePresentationMapper.mapToDomain(notePresentation)
            updateNote(note).collect {
                _noteEditView.postValue(NoteEditView(loading = false))
                when (it) {
                    is Result.Success -> {
                        handleNoteUpdateSuccess()
                    }
                    is Result.Error -> {
                        handleNoteEditError(it.failure)
                    }
                }
            }
        }
    }

    private fun handleNoteUpdateSuccess() {
        _noteEditView.value = NoteEditView(message = R.string.note_update)
    }

    fun deleteANote(noteId: Long) {
        viewModelScope.launch {
            _noteEditView.postValue(NoteEditView(loading = true))
            deleteNote(noteId).collect {
                _noteEditView.postValue(NoteEditView(loading = false))
                when (it) {
                    is Result.Success -> {
                        handleNoteDeleteSuccess()
                    }
                    is Result.Error -> {
                        handleNoteEditError(it.failure)
                    }
                }
            }
        }
    }

    private fun handleNoteDeleteSuccess() {
        _noteEditView.value = NoteEditView(message = R.string.note_delete)
    }
}