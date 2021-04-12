package com.task.noteapp.features.notes.model

data class NotePresentation(
    val id: Long? = null,
    val title: String,
    val description: String,
    val imageUrl: String,
    val isEdited: Boolean? = null,
    val dateCreated: String? = null
)