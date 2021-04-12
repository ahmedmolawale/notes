package com.task.noteapp.features.notes.model.states

data class SaveNoteView(
    val saving: Boolean = false,
    val feedback: String? = null,
)