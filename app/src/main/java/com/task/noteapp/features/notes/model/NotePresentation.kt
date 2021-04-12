package com.task.noteapp.features.notes.model

data class NotePresentation(
    val id: Long = 0,
    val title: String,
    val description: String,
    val imageUrl: String,
    val isEdited: Boolean = false,
    val dateCreated: String? = null
)