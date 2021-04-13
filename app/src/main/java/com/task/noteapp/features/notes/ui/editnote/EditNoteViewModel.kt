package com.task.noteapp.features.notes.ui.editnote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.noteapp.R
import com.task.noteapp.domain.exception.Failure
import com.task.noteapp.domain.functional.Result
import com.task.noteapp.domain.usecase.notes.UpdateNote
import com.task.noteapp.features.notes.mapper.NotePresentationMapper
import com.task.noteapp.features.notes.model.NotePresentation
import com.task.noteapp.features.notes.model.states.NoteEditView
import com.task.noteapp.features.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val updateNote: UpdateNote,
    private val notePresentationMapper: NotePresentationMapper
) : ViewModel() {


    private val noteId = MutableLiveData<Long>()

    // Two-way databinding, exposing MutableLiveData
    val title = MutableLiveData<String>()

    // Two-way databinding, exposing MutableLiveData
    val description = MutableLiveData<String>()

    // Two-way databinding, exposing MutableLiveData
    val imageUrl = MutableLiveData<String>()

    private val _noteUpdateView = MutableLiveData<Event<NoteEditView>>()
    val noteEditView: LiveData<Event<NoteEditView>>
        get() = _noteUpdateView


    //called via data-binding from the layout
    fun updateNote() {
        val currentNoteId = noteId.value
        val currentTitle = title.value
        val currentDescription = description.value
        val currentImageUrl = imageUrl.value

        if (currentTitle.isNullOrBlank() || currentDescription.isNullOrBlank()) {
            _noteUpdateView.value = Event(NoteEditView(message = R.string.no_title_description))
            return
        }
        val notePresentation = NotePresentation(
            id = currentNoteId!!,
            title = currentTitle,
            description = currentDescription,
            imageUrl = currentImageUrl ?: ""
        )
        print("Updating...")
        updateANote(notePresentation)
    }

    private fun updateANote(notePresentation: NotePresentation) {
        viewModelScope.launch {
            _noteUpdateView.value = Event(NoteEditView(message = R.string.updating_note))
            val note = notePresentationMapper.mapToDomain(notePresentation)
            updateNote(note).collect {
                when (it) {
                    is Result.Success -> {
                        handleNoteUpdateSuccess()
                    }
                    is Result.Error -> {
                        handleNoteUpdateError(it.failure)
                    }
                }
            }
        }
    }

    private fun handleNoteUpdateError(failure: Failure) {
        when (failure) {
            is Failure.NoteNotFound -> {
                _noteUpdateView.value = Event(NoteEditView(message = R.string.note_not_exist))
            }
            else -> {
                _noteUpdateView.value = Event(NoteEditView(message = R.string.error_on_note))
            }
        }
    }

    private fun handleNoteUpdateSuccess() {
        _noteUpdateView.value = Event(NoteEditView(message = R.string.note_update, updated = true))
    }

    fun setNote(note: NotePresentation) {
        noteId.value = note.id
        title.value = note.title
        description.value = note.description
        imageUrl.value = note.imageUrl
    }
}