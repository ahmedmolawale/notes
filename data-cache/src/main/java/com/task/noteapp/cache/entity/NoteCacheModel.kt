package com.task.noteapp.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class NoteCacheModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val imageUrl: String,
    var edited: Boolean = false,
    var createdAt: Date = Date()
)