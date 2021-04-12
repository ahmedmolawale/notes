package com.task.noteapp.cache.cacheImpl


import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.task.noteapp.cache.entity.DummyData
import com.task.noteapp.cache.mapper.NoteCacheModelMapper
import com.task.noteapp.cache.room.NoteDatabase
import com.task.noteapp.data.contract.cache.NoteCache
import com.task.noteapp.data.model.NoteEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class NoteCacheImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var noteCache: NoteCache
    private lateinit var noteDatabase: NoteDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        noteDatabase = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        noteCache = NoteCacheImpl(
            noteDatabase.notesDao,
            NoteCacheModelMapper()
        )
    }

    @Test
    fun `check that insertNote inserts data into database`() = runBlockingTest {
        val noteEntity: NoteEntity = DummyData.noteEntity
        noteCache.saveNote(noteEntity)
        val result = noteCache.fetchNotes().take(1).toList().first()[0]
        assertThat(noteEntity.title).isEqualTo(result.title)
        assertThat(noteEntity.description).isEqualTo(result.description)
        assertThat(noteEntity.imageUrl).isEqualTo(result.imageUrl)
        assertThat(noteEntity.edited).isFalse()
    }

    @Test
    fun `check that fetchNotes returns all data in database`() = runBlockingTest {
        val noteEntity: NoteEntity = DummyData.noteEntity
        noteCache.saveNote(noteEntity)

        val result = noteCache.fetchNotes().take(1).toList()
        assertThat(result.size).isEqualTo(1)
    }

    @Test
    fun `check that fetchNote returns the correct data from database`() = runBlockingTest {
        val noteEntity: NoteEntity = DummyData.noteEntity
        noteCache.saveNote(noteEntity)

        val result = noteCache.fetchNote(noteId = noteEntity.id)
        assertThat(result).isNotNull()
        assertThat(noteEntity.title).isEqualTo(result!!.title)
        assertThat(noteEntity.description).isEqualTo(result.description)
        assertThat(noteEntity.imageUrl).isEqualTo(result.imageUrl)
        assertThat(noteEntity.edited).isFalse()
    }

    @Test
    fun `check that fetchNote returns no data when note id is not in database`() = runBlockingTest {
        val result = noteCache.fetchNote(noteId = 1)
        assertThat(result).isNull()
    }

    @Test
    fun `check that updateNote properly update note`() = runBlockingTest {
        val noteEntity: NoteEntity = DummyData.noteEntity
        noteCache.saveNote(noteEntity)

        val noteEntityUpdate: NoteEntity =
            noteEntity.copy(title = "Update", description = "Updated reminder")
        noteCache.updateNote(noteEntityUpdate)

        val result = noteCache.fetchNote(noteId = noteEntity.id)
        assertThat(result).isNotNull()
        assertThat(result!!.title).isEqualTo(noteEntityUpdate.title)
        assertThat(result.description).isEqualTo(noteEntityUpdate.description)
        assertThat(result.edited).isTrue()
    }

    @Test
    fun `check that updateNote does not update when note id is not in database`() =
        runBlockingTest {
            val noteEntity: NoteEntity = DummyData.noteEntity
            val res = noteCache.updateNote(noteEntity)
            assertThat(res).isLessThan(1)
        }

    @Test
    fun `check that deleteNote remove note from database`() = runBlockingTest {
        val noteEntity: NoteEntity = DummyData.noteEntity
        noteCache.saveNote(noteEntity)

        noteCache.deleteNote(noteEntity.id)

        val result = noteCache.fetchNote(noteId = noteEntity.id)
        assertThat(result).isNull()
    }

    @Test
    fun `check that deleteNote does not delete when note id is not in database`() =
        runBlockingTest {
            val noteEntity: NoteEntity = DummyData.noteEntity
            val res = noteCache.deleteNote(noteEntity.id)
            assertThat(res).isLessThan(1)
        }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        runBlockingTest {
            noteDatabase.clearAllTables()
        }
        noteDatabase.close()
    }

}