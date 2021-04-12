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

    private val _savingNote = MutableLiveData<Event<Boolean>>()
    val savingNote: LiveData<Event<Boolean>>
        get() = _savingNote

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    private fun createNote(notePresentation: NotePresentation) {
        viewModelScope.launch {
            _savingNote.postValue(Event(true))
            val note: Note = notePresentationMapper.mapToDomain(notePresentation)
            saveNote(note).collect {
                _savingNote.postValue(Event(false))
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
        _snackbarText.value = Event(R.string.note_saved)
    }

    private fun handleNoteSaveError(failure: Failure) {
        _snackbarText.value = Event(R.string.note_save_error)
    }

    //called via data-binding from the layout
    fun saveNote() {
        val currentTitle = title.value
        val currentDescription = description.value
        val currentImageUrl = imageUrl.value

        if (currentTitle.isNullOrBlank() || currentDescription.isNullOrBlank()) {
            _snackbarText.value = Event(R.string.no_title_description)
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