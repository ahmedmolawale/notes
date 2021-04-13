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
                DeleteNote(fakeNoteRepository)
            )
    }

    @Test
    fun `check that deleteNote returns the correct data`() {
        fakeNoteRepository.longResponseType = ResponseType.DATA
        noteDetailViewModel.deleteANote(DummyData.note.id)

        val res = noteDetailViewModel.noteDetailView.getOrAwaitValueTest()
        assertThat(res.peekContent().message).isEqualTo(R.string.note_delete)
    }

    @Test
    fun `check that deleteNote returns not exist when note does not exist`() {
        fakeNoteRepository.longResponseType = ResponseType.RESOURCE_NOT_FOUND
        noteDetailViewModel.deleteANote(DummyData.note.id)

        val res = noteDetailViewModel.noteDetailView.getOrAwaitValueTest()
        assertThat(res).isNotNull()
        assertThat(res.peekContent().message).isEqualTo(R.string.note_not_exist)
    }

    @Test
    fun `check that deleteNote returns error if error happens`() {
        fakeNoteRepository.longResponseType = ResponseType.ERROR
        noteDetailViewModel.deleteANote(DummyData.note.id)

        val res = noteDetailViewModel.noteDetailView.getOrAwaitValueTest()
        assertThat(res).isNotNull()
        assertThat(res.peekContent().message).isEqualTo(R.string.error_on_note)
    }
}