package com.task.noteapp.features.notes.mapper

import com.task.noteapp.domain.model.Note
import com.task.noteapp.features.notes.mapper.base.PresentationMapper
import com.task.noteapp.features.notes.model.NotePresentation
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NotePresentationMapper @Inject constructor(): PresentationMapper<Note, NotePresentation> {
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
            id = presentation.id,
            title = presentation.title,
            description = presentation.description,
            imageUrl = presentation.imageUrl,
            edited = presentation.isEdited,
            createdAt = Date()
        )
    }
}