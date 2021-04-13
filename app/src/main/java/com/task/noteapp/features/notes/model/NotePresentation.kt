package com.task.noteapp.features.notes.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NotePresentation(
    val id: Long = 0,
    val title: String,
    val description: String,
    val imageUrl: String,
    val isEdited: Boolean = false,
    val dateCreated: String? = null
): Parcelable