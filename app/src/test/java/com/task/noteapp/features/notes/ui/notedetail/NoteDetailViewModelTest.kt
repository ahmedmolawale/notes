package com.task.noteapp.features.notes.ui.notedetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.task.noteapp.R
import com.task.noteapp.domain.usecase.notes.DeleteNote
import com.task.noteapp.domain.usecase.notes.FetchNote
import com.task.noteapp.features.notes.data.DummyData
import com.task.noteapp.features.notes.fakes.FakeNoteRepository
import com.task.noteapp.features.notes.mapper.NotePresentationMapper
import com.task.noteapp.features.utils.MainCoroutineRule
import com.task.noteapp.features.utils.ResponseType
import com.task.noteapp.features.utils.getOrAwaitValueTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NoteDetailViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var noteDetailViewModel: NoteDetailViewModel
    private lateinit var notePresentationMapper: NotePresentationMapper

    @Before
    fun setup() {
        fakeNoteRepository = FakeNoteRepository()
        notePresentationMapper = NotePresentationMapper()
        noteDetailViewModel =
            NoteDetailViewModel(
                DeleteNote(fakeNoteRepository),
                FetchNote(fakeNoteRepository),
                notePresentationMapper
            )
    }

    @Test
    fun `check that fetchNote returns the correct data`() {
        fakeNoteRepository.noteResponseType = ResponseType.DATA
        noteDetailViewModel.getNote(DummyData.note.id)

        val res = noteDetailViewModel.noteView.getOrAwaitValueTest()
        assertThat(res.note).isNotNull()
        assertThat(res.note)
            .isEqualTo(notePresentationMapper.mapToPresentation(DummyData.note))
    }

    @Test
    fun `check that fetchNote returns not exist when note does not exist`() {
        fakeNoteRepository.noteResponseType = ResponseType.RESOURCE_NOT_FOUND
        noteDetailViewModel.getNote(DummyData.note.id)

        val res = noteDetailViewModel.noteView.getOrAwaitValueTest()
        assertThat(res.note).isNull()
        assertThat(res.message).isEqualTo(R.string.note_not_exist)
    }

    @Test
    fun `check that fetchNote returns error if error happens`() {
        fakeNoteRepository.noteResponseType = ResponseType.ERROR
        noteDetailViewModel.getNote(DummyData.note.id)

        val res = noteDetailViewModel.noteView.getOrAwaitValueTest()
        assertThat(res.note).isNull()
        assertThat(res.message).isEqualTo(R.string.error_on_note)
    }

    @Test
    fun `check that deleteNote returns the correct data`() {
        fakeNoteRepository.longResponseType = ResponseType.DATA
        noteDetailViewModel.deleteANote(DummyData.note.id)

        val res = noteDetailViewModel.noteView.getOrAwaitValueTest()
        assertThat(res.message).isEqualTo(R.string.note_delete)
    }

    @Test
    fun `check that deleteNote returns not exist when note does not exist`() {
        fakeNoteRepository.longResponseType = ResponseType.RESOURCE_NOT_FOUND
        noteDetailViewModel.deleteANote(DummyData.note.id)

        val res = noteDetailViewModel.noteView.getOrAwaitValueTest()
        assertThat(res.note).isNull()
        assertThat(res.message).isEqualTo(R.string.note_not_exist)
    }

    @Test
    fun `check that deleteNote returns error if error happens`() {
        fakeNoteRepository.longResponseType = ResponseType.ERROR
        noteDetailViewModel.deleteANote(DummyData.note.id)

        val res = noteDetailViewModel.noteView.getOrAwaitValueTest()
        assertThat(res.note).isNull()
        assertThat(res.message).isEqualTo(R.string.error_on_note)
    }
}