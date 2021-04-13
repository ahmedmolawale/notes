package com.task.noteapp.features.notes.ui.notedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.noteapp.R
import com.task.noteapp.domain.exception.Failure
import com.task.noteapp.domain.functional.Result
import com.task.noteapp.domain.usecase.notes.DeleteNote
import com.task.noteapp.domain.usecase.notes.FetchNote
import com.task.noteapp.features.notes.mapper.NotePresentationMapper
import com.task.noteapp.features.notes.model.NotePresentation
import com.task.noteapp.features.notes.model.states.NoteDetailView
import com.task.noteapp.features.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val deleteNote: DeleteNote,
    private val fetchNote: FetchNote,
    private val notePresentationMapper: NotePresentationMapper
) : ViewModel() {

    private val _note = MutableLiveData<NotePresentation>()
    val note: LiveData<NotePresentation>
        get() = _note

    private val _noteDetailView = MutableLiveData<Event<NoteDetailView>>()
    val noteDetailView: LiveData<Event<NoteDetailView>> = _noteDetailView

    private fun handleNoteEditError(failure: Failure) {
        when (failure) {
            is Failure.NoteNotFound -> {
                _noteDetailView.value = Event(NoteDetailView(message = R.string.note_not_exist))
            }
            else -> {
                _noteDetailView.value = Event(NoteDetailView(message = R.string.error_on_note))
            }
        }
    }

    fun deleteANote(noteId: Long) {
        viewModelScope.launch {
            _noteDetailView.value = Event(NoteDetailView(message = R.string.deleting_note))
            deleteNote(noteId).collect {
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
        _noteDetailView.value = Event(NoteDetailView(message = R.string.note_delete))
    }

    fun setNote(note: NotePresentation) {
        _note.value = note
    }

    /**
     * Called via Data Binding through FAB click listener.
     */
    fun editNote() {
        _noteDetailView.value = Event(NoteDetailView(editNote = true))
    }
}