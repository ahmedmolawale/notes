package com.task.noteapp.cache.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.task.noteapp.cache.entity.NoteCacheModel

@Database(
    entities = [NoteCacheModel::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class NoteDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "note_database"
    }

    abstract val notesDao: NotesDao
}