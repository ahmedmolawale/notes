package com.task.noteapp.cache.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.task.noteapp.cache.entity.NoteCacheModel
import kotlinx.coroutines.flow.Flow


@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteCacheModel: NoteCacheModel)

    @Query("SELECT * FROM notes")
    fun fetchNotes(): Flow<List<NoteCacheModel>>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun fetchANote(noteId: String): NoteCacheModel?

    @Update
    suspend fun updateANote(noteCacheModel: NoteCacheModel)

    @Query("DELETE FROM notes WHERE id = :noteId")
    suspend fun deleteANote(noteId: Long)

}