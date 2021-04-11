package com.task.noteapp.data.model

import java.util.*

data class NoteEntity(
    val id: Long,
    val title: String,
    val description: String,
    val imageUrl: String,
    val edited: Boolean,
    val createdAt: Date
)