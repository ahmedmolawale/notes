package com.task.noteapp.features.notes.ui.addnote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.noteapp.R
import com.task.noteapp.domain.exception.Failure
import com.task.noteapp.domain.functional.Result
import com.task.noteapp.domain.model.Note
import com.task.noteapp.domain.usecase.notes.SaveNote
import com.task.noteapp.features.notes.mapper.NotePresentationMapper
import com.task.noteapp.features.notes.model.NotePresentation
import com.task.noteapp.features.notes.model.states.NoteSaveView
import com.task.noteapp.features.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val saveNote: SaveNote,
    private val notePresentationMapper: NotePresentationMapper
) : ViewModel() {

    // Two-way databinding, exposing MutableLiveData
    val title = MutableLiveData<String>()

    // Two-way databinding, exposing MutableLiveData
    val description = MutableLiveData<String>()

    // Two-way databinding, exposing MutableLiveData
    val imageUrl = MutableLiveData<String>()


    private val _noteSaveView = MutableLiveData<Event<NoteSaveView>>()
    val noteSaveView: LiveData<Event<NoteSaveView>> = _noteSaveView


    private fun createNote(notePresentation: NotePresentation) {
        viewModelScope.launch {
            _noteSaveView.value = Event(NoteSaveView(message = R.string.saving_note))
            val note: Note = notePresentationMapper.mapToDomain(notePresentation)
            saveNote(note).collect {
                when (it) {
                    is Result.Error -> {
                        handleNoteSaveError(it.failure)
                    }
                    is Result.Success -> {
                        handleNoteSaveSuccess()
                    }
                }
            }
        }
    }

    private fun handleNoteSaveSuccess() {
        _noteSaveView.value = Event(NoteSaveView(message = R.string.note_saved, noteSaved = true))
    }

    private fun handleNoteSaveError(failure: Failure) {
        _noteSaveView.value = Event(NoteSaveView(message = R.string.note_save_error))
    }

    //called via data-binding from the layout
    fun saveNote() {
        val currentTitle = title.value
        val currentDescription = description.value
        val currentImageUrl = imageUrl.value

        if (currentTitle.isNullOrBlank() || currentDescription.isNullOrBlank()) {
            _noteSaveView.value = Event(NoteSaveView(message = R.string.no_title_description))
            return
        }
        val notePresentation = NotePresentation(
            title = currentTitle,
            description = currentDescription,
            imageUrl = currentImageUrl ?: ""
        )
        createNote(notePresentation)
    }
}