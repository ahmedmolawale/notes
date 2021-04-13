package com.task.noteapp.features.notes.model.states

import com.task.noteapp.features.notes.model.NotePresentation

data class NoteDetailView(
    val message: Int? = null,
    val deleted: Boolean? = null,
    val editNote: Boolean? = null,
    val note: NotePresentation? = null
)