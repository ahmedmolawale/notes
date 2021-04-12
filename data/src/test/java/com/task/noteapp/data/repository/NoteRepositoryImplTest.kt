package com.task.noteapp.data.repository

import com.google.common.truth.Truth.assertThat
import com.task.noteapp.data.contract.cache.NoteCache
import com.task.noteapp.data.fakes.FakeNoteCache
import com.task.noteapp.data.mapper.NoteEntityMapper
import com.task.noteapp.data.model.DummyData
import com.task.noteapp.domain.exception.Failure
import com.task.noteapp.domain.functional.Result
import com.task.noteapp.domain.model.Note
import com.task.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class NoteRepositoryImplTest {

    private lateinit var noteEntityMapper: NoteEntityMapper
    private lateinit var noteCache: NoteCache
    private lateinit var noteRepository: NoteRepository

    @Before
    fun setup() {
        noteEntityMapper = NoteEntityMapper()
        noteCache = FakeNoteCache()
        noteRepository = NoteRepositoryImpl(noteCache, noteEntityMapper)
    }

    @Test
    fun `check that saveNote correctly saves the note`() = runBlockingTest {
        val note: Note = DummyData.note
        noteRepository.saveNote(note).collect()
        noteRepository.getNotes().collect {
            assertThat(it).isInstanceOf(Result.Success::class.java)
            it as Result.Success
            assertThat(it.data).isEqualTo(listOf(note))
        }
    }

    @Test
    fun `check that getNotes correctly returns the notes`() = runBlockingTest {
        val note: Note = DummyData.note
        val note2: Note = note.copy(id = 2, title = "Lover birds", description = "Love in Tokyo")
        noteRepository.saveNote(note).collect()
        noteRepository.saveNote(note2).collect()

        val allNotes: List<Note> = listOf(note, note2)
        noteRepository.getNotes().collect {
            assertThat(it).isInstanceOf(Result.Success::class.java)
            it as Result.Success
            assertThat(allNotes).hasSize(it.data.size)
            assertThat(allNotes).containsExactlyElementsIn(it.data)
        }
    }

    @Test
    fun `check that fetchNote correctly returns the actual note`() = runBlockingTest {
        val note: Note = DummyData.note
        noteRepository.saveNote(note).collect()
        noteRepository.fetchNote(noteId = note.id).collect {
            assertThat(it).isInstanceOf(Result.Success::class.java)
            it as Result.Success
            assertThat(it.data).isNotNull()
            assertThat(note).isEqualTo(it.data)
        }
    }

    @Test
    fun `check that fetchNote returns error when note does not exist`() = runBlockingTest {
        val note: Note = DummyData.note
        noteRepository.fetchNote(noteId = note.id).collect {
            assertThat(it).isInstanceOf(Result.Error::class.java)
            it as Result.Error
            assertThat(it.failure).isNotNull()
            assertThat(it.failure).isInstanceOf(Failure.NoteNotFound::class.java)
        }
    }

    @Test
    fun `check that updateNote correctly updates the note`() = runBlockingTest {
        val note: Note = DummyData.note
        noteRepository.saveNote(note).collect()
        val toUpdate: Note = note.copy(title = "Holla")
        noteRepository.updateNote(toUpdate).collect()
        noteRepository.fetchNote(noteId = note.id).collect {
            assertThat(it).isInstanceOf(Result.Success::class.java)
            it as Result.Success
            assertThat(it.data).isNotNull()
            assertThat(it.data.edited).isTrue()
            assertThat(it.data.title).isEqualTo(toUpdate.title)
        }
    }

    @Test
    fun `check that updateNote returns error when note does not exist`() = runBlockingTest {
        val note: Note = DummyData.note
        noteRepository.updateNote(note).collect()
        noteRepository.fetchNote(noteId = note.id).collect {
            assertThat(it).isInstanceOf(Result.Error::class.java)
            it as Result.Error
            assertThat(it.failure).isNotNull()
            assertThat(it.failure).isInstanceOf(Failure.NoteNotFound::class.java)
        }
    }

    @Test
    fun `check that deleteNote correctly deletes the note`() = runBlockingTest {
        val note: Note = DummyData.note
        noteRepository.saveNote(note).collect()
        noteRepository.deleteNote(note.id).collect()
        noteRepository.fetchNote(noteId = note.id).collect {
            assertThat(it).isInstanceOf(Result.Error::class.java)
            it as Result.Error
            assertThat(it.failure).isNotNull()
            assertThat(it.failure).isInstanceOf(Failure.NoteNotFound::class.java)
        }
    }
}