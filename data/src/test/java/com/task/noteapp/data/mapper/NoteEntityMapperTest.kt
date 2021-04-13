package com.task.noteapp.data.mapper

import com.google.common.truth.Truth.assertThat
import com.task.noteapp.data.model.DummyData
import com.task.noteapp.data.model.NoteEntity
import com.task.noteapp.domain.model.Note
import org.junit.Test

class NoteEntityMapperTest {
    private val noteEntityMapper: NoteEntityMapper = NoteEntityMapper()

    @Test
    fun `check that mapToDomain returns correct data`() {
        val noteEntity: NoteEntity = DummyData.noteEntity
        val note: Note = noteEntityMapper.mapToDomain(noteEntity)
        assertThat(noteEntity.id).isEqualTo(note.id)
        assertThat(noteEntity.title).isEqualTo(note.title)
        assertThat(noteEntity.description).isEqualTo(note.description)
        assertThat(noteEntity.imageUrl).isEqualTo(note.imageUrl)
        assertThat(noteEntity.edited).isEqualTo(note.edited)
        assertThat(noteEntity.createdAt).isEqualTo(note.createdAt)
    }

    @Test
    fun `check that mapToEntity returns correct data`() {
        val note: Note = DummyData.note
        val noteEntity: NoteEntity = noteEntityMapper.mapToEntity(note)
        assertThat(note.id).isEqualTo(noteEntity.id)
        assertThat(note.title).isEqualTo(noteEntity.title)
        assertThat(note.description).isEqualTo(noteEntity.description)
        assertThat(note.imageUrl).isEqualTo(noteEntity.imageUrl)
        assertThat(note.edited).isEqualTo(noteEntity.edited)
        assertThat(note.createdAt).isEqualTo(noteEntity.createdAt)
    }

    @Test
    fun `check that mapToEntityList returns correct data`() {
        val notes: List<Note> = listOf(DummyData.note)
        val noteEntityList: List<NoteEntity> =
            noteEntityMapper.mapToEntityList(notes)
        assertThat(noteEntityList).isNotEmpty()
        assertThat(noteEntityList).hasSize(notes.size)
        val note = notes[0]
        val noteEntity = noteEntityList[0]
        assertThat(note.id).isEqualTo(noteEntity.id)
        assertThat(note.title).isEqualTo(noteEntity.title)
        assertThat(note.description).isEqualTo(noteEntity.description)
        assertThat(note.imageUrl).isEqualTo(noteEntity.imageUrl)
        assertThat(note.edited).isEqualTo(noteEntity.edited)
        assertThat(note.createdAt).isEqualTo(noteEntity.createdAt)
    }

    @Test
    fun `check that mapToDomainList returns correct data`() {
        val noteEntityList: List<NoteEntity> = listOf(DummyData.noteEntity)
        val notes: List<Note> =
            noteEntityMapper.mapToDomainList(noteEntityList)
        assertThat(notes).isNotEmpty()
        assertThat(notes).hasSize(noteEntityList.size)
        val note = notes[0]
        val noteEntity = noteEntityList[0]
        assertThat(note.id).isEqualTo(noteEntity.id)
        assertThat(note.title).isEqualTo(noteEntity.title)
        assertThat(note.description).isEqualTo(noteEntity.description)
        assertThat(note.imageUrl).isEqualTo(noteEntity.imageUrl)
        assertThat(note.edited).isEqualTo(noteEntity.edited)
        assertThat(note.createdAt).isEqualTo(noteEntity.createdAt)
    }
}
