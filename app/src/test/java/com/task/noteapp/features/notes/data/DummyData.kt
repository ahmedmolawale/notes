package com.task.noteapp.features.notes.data

import com.task.noteapp.domain.model.Note
import com.task.noteapp.features.notes.model.NotePresentation
import java.util.*

object DummyData {

    val notePresentation = NotePresentation(
        title = "Many men",
        description = "34.BBY",
        imageUrl = "https://swapi.dev/people/21"
    )

    val note = Note(
        id = 0,
        title = "Many men",
        description = "34.BBY",
        imageUrl = "https://swapi.dev/people/21",
        edited = false,
        createdAt = Date()
    )

    val noteList: List<Note> = listOf(note)
}