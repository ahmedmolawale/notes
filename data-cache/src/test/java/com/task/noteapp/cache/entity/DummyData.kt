package com.task.noteapp.cache.entity

import com.task.noteapp.data.model.NoteEntity
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

    val noteCacheModel: NoteCacheModel = NoteCacheModel(
        id = 1,
        title = "Reminder",
        description = "Remind me about swimming",
        imageUrl = "https://image.com/wale-ahmed",
        edited = false,
        createdAt = Date()
    )

}