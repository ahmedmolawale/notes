package com.task.noteapp.data.model

import com.task.noteapp.domain.model.Note
import java.util.*

object DummyData {

    val noteEntity: NoteEntity = NoteEntity(
        id = 1,
        title = "Reminder",
        description = "Remind me about gym",
        imageUrl = "https://image.com/wale",
        edited = false,
        createdAt = Date()
    )

    val note: Note = Note(
        id = 1,
        title = "Reminder",
        description = "Remind me about swimming",
        imageUrl = "https://image.com/wale-ahmed",
        edited = false,
        createdAt = Date()
    )

}