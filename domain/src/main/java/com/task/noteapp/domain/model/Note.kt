package com.task.noteapp.domain.model

import java.util.*

data class Note(
    val id: Long,
    val title: String,
    val description: String,
    val imageUrl: String,
    val edited: Boolean,
    val createdAt: Date
)