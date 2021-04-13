package com.task.noteapp.features.notes.model.states

import com.task.noteapp.features.notes.model.NotePresentation

data class NoteListView(
    val loading: Boolean = false,
    val errorMessage: Int? = null,
    val isEmpty: Boolean = false,
    val notes: List<NotePresentation>? = null
)
