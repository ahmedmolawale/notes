package com.task.noteapp.features.notes.mapper

import com.task.noteapp.domain.model.Note
import com.task.noteapp.features.notes.mapper.base.PresentationMapper
import com.task.noteapp.features.notes.model.NotePresentation
import java.text.SimpleDateFormat
import java.util.*

class NotePresentationMapper : PresentationMapper<Note, NotePresentation> {
    override fun mapToPresentation(domain: Note): NotePresentation {
        return NotePresentation(
            id = domain.id,
            title = domain.title,
            description = domain.description,
            imageUrl = domain.imageUrl,
            isEdited = domain.edited,
            dateCreated = SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.getDefault()
            ).format(domain.createdAt)
        )
    }

    override fun mapToDomain(presentation: NotePresentation): Note {
        return Note(
            id = presentation.id ?: 0,
            title = presentation.title,
            description = presentation.description,
            imageUrl = presentation.imageUrl,
            edited = presentation.isEdited ?: false,
            createdAt = Date()
        )
    }
}