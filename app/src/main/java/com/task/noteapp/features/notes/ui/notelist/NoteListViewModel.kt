package com.task.noteapp.features.notes.ui.notelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.noteapp.R
import com.task.noteapp.domain.exception.Failure
import com.task.noteapp.domain.functional.Result
import com.task.noteapp.domain.model.Note
import com.task.noteapp.domain.usecase.notes.GetNotes
import com.task.noteapp.features.notes.mapper.NotePresentationMapper
import com.task.noteapp.features.notes.model.NotePresentation
import com.task.noteapp.features.notes.model.states.NoteListView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val getNotes: GetNotes,
    private val notePresentationMapper: NotePresentationMapper
) : ViewModel() {

    private val _noteListView = MutableLiveData<NoteListView>()
    val noteListView: LiveData<NoteListView>
        get() = _noteListView

    fun fetchNotes() {
        viewModelScope.launch {
            _noteListView.postValue(NoteListView(loading = true))
            getNotes().collect {
                when (it) {
                    is Result.Success -> {
                        handleNotesSuccess(it.data)
                    }
                    is Result.Error -> {
                        handleNotesError(it.failure)
                    }
                }
            }
        }
    }

    private fun handleNotesSuccess(notes: List<Note>) {
        if (notes.isEmpty()) {
            _noteListView.postValue(NoteListView(isEmpty = true))
        } else {
            val notePresentationList: List<NotePresentation> =
                notes.map(notePresentationMapper::mapToPresentation)
            _noteListView.postValue(NoteListView(notes = notePresentationList))
        }
    }

    private fun handleNotesError(failure: Failure) {
        _noteListView.postValue(NoteListView(errorMessage = R.string.error_on_notes))
    }
}