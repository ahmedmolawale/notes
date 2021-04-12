package com.task.noteapp.features.notes.model.states

import com.task.noteapp.features.notes.model.NotePresentation

data class NoteEditView(
    val loading: Boolean = false,
    val note: NotePresentation? = null,
    val message: Int? = null
)