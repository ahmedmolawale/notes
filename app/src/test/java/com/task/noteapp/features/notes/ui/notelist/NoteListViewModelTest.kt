package com.task.noteapp.features.notes.ui.notelist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.task.noteapp.R
import com.task.noteapp.domain.usecase.notes.GetNotes
import com.task.noteapp.features.notes.data.DummyData
import com.task.noteapp.features.notes.fakes.FakeNoteRepository
import com.task.noteapp.features.notes.mapper.NotePresentationMapper
import com.task.noteapp.features.utils.MainCoroutineRule
import com.task.noteapp.features.utils.ResponseType
import com.task.noteapp.features.utils.getOrAwaitValueTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NoteListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var noteListViewModel: NoteListViewModel
    private lateinit var notePresentationMapper: NotePresentationMapper
    private lateinit var getNotes: GetNotes

    @Before
    fun setup() {
        fakeNoteRepository = FakeNoteRepository()
        notePresentationMapper = NotePresentationMapper()
        getNotes = GetNotes(fakeNoteRepository)
        noteListViewModel = NoteListViewModel(getNotes, notePresentationMapper)
    }

    @Test
    fun `check that fetchNotes returns correct data`() {
        fakeNoteRepository.noteListResponseType = ResponseType.DATA
        noteListViewModel.fetchNotes()
        val res = noteListViewModel.noteListView.getOrAwaitValueTest()
        assertThat(res.errorMessage).isNull()
        assertThat(res.isEmpty).isFalse()
        assertThat(res.notes).isEqualTo(DummyData.noteList.map(notePresentationMapper::mapToPresentation))
    }

    @Test
    fun `check that fetchNotes returns empty data`() {
        fakeNoteRepository.noteListResponseType = ResponseType.EMPTY_DATA
        noteListViewModel.fetchNotes()
        val res = noteListViewModel.noteListView.getOrAwaitValueTest()
        assertThat(res.errorMessage).isNull()
        assertThat(res.isEmpty).isTrue()
        assertThat(res.notes).isNull()
    }

    @Test
    fun `check that fetchNotes returns error`() {
        fakeNoteRepository.noteListResponseType = ResponseType.ERROR
        noteListViewModel.fetchNotes()
        val res = noteListViewModel.noteListView.getOrAwaitValueTest()
        assertThat(res.errorMessage).isNotNull()
        assertThat(res.errorMessage).isEqualTo(R.string.error_on_notes)
        assertThat(res.notes).isNull()
    }
}
