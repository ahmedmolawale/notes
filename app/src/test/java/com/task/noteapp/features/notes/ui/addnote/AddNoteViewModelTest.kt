package com.task.noteapp.features.notes.ui.addnote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.task.noteapp.R
import com.task.noteapp.domain.usecase.notes.SaveNote
import com.task.noteapp.features.notes.fakes.FakeNoteRepository
import com.task.noteapp.features.notes.mapper.NotePresentationMapper
import com.task.noteapp.features.utils.MainCoroutineRule
import com.task.noteapp.features.utils.ResponseType
import com.task.noteapp.features.utils.getOrAwaitValueTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddNoteViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var addNoteViewModel: AddNoteViewModel
    private lateinit var notePresentationMapper: NotePresentationMapper

    @Before
    fun setup() {
        fakeNoteRepository = FakeNoteRepository()
        notePresentationMapper = NotePresentationMapper()
        addNoteViewModel = AddNoteViewModel(SaveNote(fakeNoteRepository), notePresentationMapper)
    }

    @Test
    fun `check that saveNote fails when title is not available`() {
        addNoteViewModel.title.value = ""
        addNoteViewModel.saveNote()
        val res = addNoteViewModel.noteSaveView.getOrAwaitValueTest()
        assertThat(res.peekContent()).isNotNull()
        assertThat(res.peekContent().message).isEqualTo(R.string.no_title_description)
    }

    @Test
    fun `check that saveNote fails when description is not available`() {
        addNoteViewModel.description.value = ""
        addNoteViewModel.saveNote()
        val res = addNoteViewModel.noteSaveView.getOrAwaitValueTest()
        assertThat(res.peekContent()).isNotNull()
        assertThat(res.peekContent().message).isEqualTo(R.string.no_title_description)
    }

    @Test
    fun `check that saveNote saves data`() {
        addNoteViewModel.title.value = "Tell me"
        addNoteViewModel.description.value = "I am a whole bunch"
        fakeNoteRepository.noteResponseType = ResponseType.DATA

        addNoteViewModel.saveNote()
        val res = addNoteViewModel.noteSaveView.getOrAwaitValueTest()
        assertThat(res.peekContent()).isNotNull()
        assertThat(res.peekContent().message).isEqualTo(R.string.note_saved)
    }

    @Test
    fun `check that saveNote show error when unable to save note`() {
        addNoteViewModel.title.value = "Tell me"
        addNoteViewModel.description.value = "I am a whole bunch"
        fakeNoteRepository.noteResponseType = ResponseType.ERROR

        addNoteViewModel.saveNote()
        val res = addNoteViewModel.noteSaveView.getOrAwaitValueTest()
        assertThat(res.peekContent()).isNotNull()
        assertThat(res.peekContent().message).isEqualTo(R.string.note_save_error)
    }
}