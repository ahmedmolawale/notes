package com.task.noteapp.features.notes.mapper

import com.google.common.truth.Truth.assertThat
import com.task.noteapp.domain.model.Note
import com.task.noteapp.features.notes.data.DummyData
import com.task.noteapp.features.notes.model.NotePresentation
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*


class NotePresentationMapperTest {

    private val notePresentationMapper: NotePresentationMapper = NotePresentationMapper()

    @Test
    fun `check that mapToPresentation returns correct data`() {
        val note: Note = DummyData.note
        val notePresentation: NotePresentation = notePresentationMapper.mapToPresentation(note)
        val actualDateString =
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(note.createdAt)
        assertThat(note.id).isEqualTo(notePresentation.id)
        assertThat(note.title).isEqualTo(notePresentation.title)
        assertThat(note.description).isEqualTo(notePresentation.description)
        assertThat(note.imageUrl).isEqualTo(notePresentation.imageUrl)
        assertThat(note.edited).isEqualTo(notePresentation.isEdited)
        assertThat(actualDateString).isEqualTo(notePresentation.dateCreated)
    }

    @Test
    fun `check that mapToDomain returns correct data`() {
        val notePresentation: NotePresentation = DummyData.notePresentation
        val note: Note = notePresentationMapper.mapToDomain(notePresentation)
        assertThat(notePresentation.id).isEqualTo(note.id)
        assertThat(notePresentation.title).isEqualTo(note.title)
        assertThat(notePresentation.description).isEqualTo(note.description)
        assertThat(notePresentation.imageUrl).isEqualTo(note.imageUrl)
        assertThat(notePresentation.isEdited).isEqualTo(note.edited)
    }

    @Test
    fun `check that mapToPresentationList returns correct data`() {
        val notes: List<Note> = listOf(DummyData.note)
        val notePresentationList: List<NotePresentation> =
            notePresentationMapper.mapToPresentationList(notes)
        val actualDateString =
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(notes[0].createdAt)
        assertThat(notePresentationList).isNotEmpty()
        assertThat(notePresentationList).hasSize(notes.size)
        val note = notes[0]
        val notePresentation = notePresentationList[0]
        assertThat(note.id).isEqualTo(notePresentation.id)
        assertThat(note.title).isEqualTo(notePresentation.title)
        assertThat(note.description).isEqualTo(notePresentation.description)
        assertThat(note.imageUrl).isEqualTo(notePresentation.imageUrl)
        assertThat(note.edited).isEqualTo(notePresentation.isEdited)
        assertThat(actualDateString).isEqualTo(notePresentation.dateCreated)
    }

    @Test
    fun `check that mapToDomainList returns correct data`() {
        val notePresentationList: List<NotePresentation> =
            listOf(DummyData.notePresentation)
        val notes: List<Note> =
            notePresentationMapper.mapToDomainList(notePresentationList)
        assertThat(notes).isNotEmpty()
        assertThat(notes).hasSize(notePresentationList.size)
        val notePresentation = notePresentationList[0]
        val note = notes[0]
        assertThat(notePresentation.id).isEqualTo(note.id)
        assertThat(notePresentation.title).isEqualTo(note.title)
        assertThat(notePresentation.description).isEqualTo(note.description)
        assertThat(notePresentation.imageUrl).isEqualTo(note.imageUrl)
        assertThat(notePresentation.isEdited).isEqualTo(note.edited)
    }
}