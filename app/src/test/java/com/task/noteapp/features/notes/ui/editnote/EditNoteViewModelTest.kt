package com.task.noteapp.features.notes.ui.editnote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.task.noteapp.R
import com.task.noteapp.domain.usecase.notes.UpdateNote
import com.task.noteapp.features.notes.fakes.FakeNoteRepository
import com.task.noteapp.features.notes.mapper.NotePresentationMapper
import com.task.noteapp.features.utils.MainCoroutineRule
import com.task.noteapp.features.utils.ResponseType
import com.task.noteapp.features.utils.getOrAwaitValueTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EditNoteViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var editNoteViewModel: EditNoteViewModel
    private lateinit var notePresentationMapper: NotePresentationMapper

    @Before
    fun setup() {
        fakeNoteRepository = FakeNoteRepository()
        notePresentationMapper = NotePresentationMapper()
        editNoteViewModel = EditNoteViewModel(
            UpdateNote(fakeNoteRepository),
            notePresentationMapper
        )
    }

    @Test
    fun `check that updateNote fails when title is not available`() {
        editNoteViewModel.title.value = ""
        editNoteViewModel.updateNote()
        val res = editNoteViewModel.noteView.getOrAwaitValueTest()
        assertThat(res).isNotNull()
        assertThat(res.message).isEqualTo(R.string.no_title_description)
    }

    @Test
    fun `check that updateNote fails when description is not available`() {
        editNoteViewModel.description.value = ""
        editNoteViewModel.updateNote()
        val res = editNoteViewModel.noteView.getOrAwaitValueTest()
        assertThat(res).isNotNull()
        assertThat(res.message).isEqualTo(R.string.no_title_description)
    }

    @Test
    fun `check that updateNote updates note`() {
        editNoteViewModel.title.value = "Tell me"
        editNoteViewModel.description.value = "I am a whole bunch"
        fakeNoteRepository.noteResponseType = ResponseType.DATA

        editNoteViewModel.updateNote()
        val res = editNoteViewModel.noteView.getOrAwaitValueTest()
        assertThat(res).isNotNull()
        assertThat(res.message).isEqualTo(R.string.note_update)
    }

    @Test
    fun `check that updateNote returns not exist when note does not exist`() {
        editNoteViewModel.title.value = "Tell me"
        editNoteViewModel.description.value = "I am a whole bunch"
        fakeNoteRepository.noteResponseType = ResponseType.RESOURCE_NOT_FOUND
        editNoteViewModel.updateNote()

        val res = editNoteViewModel.noteView.getOrAwaitValueTest()
        assertThat(res.note).isNull()
        assertThat(res.message).isEqualTo(R.string.note_not_exist)
    }

    @Test
    fun `check that updateNotes show error when unable to save note`() {
        editNoteViewModel.title.value = "Tell me"
        editNoteViewModel.description.value = "I am a whole bunch"
        fakeNoteRepository.noteResponseType = ResponseType.ERROR

        editNoteViewModel.updateNote()
        val res = editNoteViewModel.noteView.getOrAwaitValueTest()
        assertThat(res).isNotNull()
        assertThat(res.message).isEqualTo(R.string.error_on_note)
    }

//    //for updating note
//    @Test
//    fun `check that updateNote returns the correct data`() {
//        fakeNoteRepository.noteResponseType = ResponseType.DATA
//        addNoteViewModel.updateANote(DummyData.notePresentation)
//
//        val res = addNoteViewModel.noteView.getOrAwaitValueTest()
//        assertThat(res.message).isEqualTo(R.string.note_update)
//    }
//
//    @Test
//    fun `check that updateNote returns not exist when note does not exist`() {
//        fakeNoteRepository.noteResponseType = ResponseType.RESOURCE_NOT_FOUND
//        addNoteViewModel.updateANote(DummyData.notePresentation)
//
//        val res = addNoteViewModel.noteView.getOrAwaitValueTest()
//        assertThat(res.note).isNull()
//        assertThat(res.message).isEqualTo(R.string.note_not_exist)
//    }
//
//    @Test
//    fun `check that updateNote returns error if error happens`() {
//        fakeNoteRepository.noteResponseType = ResponseType.ERROR
//        addNoteViewModel.updateANote(DummyData.notePresentation)
//
//        val res = addNoteViewModel.noteView.getOrAwaitValueTest()
//        assertThat(res.note).isNull()
//        assertThat(res.message).isEqualTo(R.string.error_on_note)
//    }
}