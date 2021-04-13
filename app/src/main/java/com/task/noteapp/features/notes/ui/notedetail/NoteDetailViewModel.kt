package com.task.noteapp.features.notes.ui.notedetail

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
import com.task.noteapp.features.notes.mapper.NotePresentationMapper
import com.task.noteapp.features.notes.model.NotePresentation
import com.task.noteapp.features.notes.model.states.NoteView
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

    private val _noteView = MutableLiveData<NoteView>()
    val noteView: LiveData<NoteView>
        get() = _noteView

    private val _editNoteEvent = MutableLiveData<Event<Unit>>()
    val editNoteEvent: LiveData<Event<Unit>> = _editNoteEvent

    fun getNote(noteId: Long) {
        viewModelScope.launch {
            _noteView.postValue(NoteView(loading = true))
            fetchNote(noteId).collect {
                _noteView.postValue(NoteView(loading = false))
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
        _noteView.postValue(
            NoteView(
                note = notePresentationMapper.mapToPresentation(
                    note
                )
            )
        )
    }

    private fun handleNoteEditError(failure: Failure) {
        when (failure) {
            is Failure.NoteNotFound -> {
                _noteView.value = NoteView(message = R.string.note_not_exist)
            }
            else -> {
                _noteView.value = NoteView(message = R.string.error_on_note)
            }
        }
    }

    fun deleteANote(noteId: Long) {
        viewModelScope.launch {
            _noteView.postValue(NoteView(loading = true))
            deleteNote(noteId).collect {
                _noteView.postValue(NoteView(loading = false))
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
        _noteView.value = NoteView(message = R.string.note_delete)
    }

    fun setNote(note: NotePresentation) {
        _noteView.postValue(
            NoteView(
                note = note
            )
        )
    }

    /**
     * Called via Data Binding through FAB click listener.
     */
    fun editNote(){
        _editNoteEvent.value = Event(Unit)
    }
}