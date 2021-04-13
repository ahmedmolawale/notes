package com.task.noteapp.data.mapper

import com.task.noteapp.data.mapper.base.EntityMapper
import com.task.noteapp.data.model.NoteEntity
import com.task.noteapp.domain.model.Note
import javax.inject.Inject

class NoteEntityMapper @Inject constructor() : EntityMapper<NoteEntity, Note> {

    override fun mapToDomain(entity: NoteEntity): Note {
        return Note(
            id = entity.id,
            title = entity.title,
            description = entity.description,
            imageUrl = entity.imageUrl,
            createdAt = entity.createdAt,
            edited = entity.edited
        )
    }

    override fun mapToEntity(domain: Note): NoteEntity {
        return NoteEntity(
            id = domain.id,
            title = domain.title,
            description = domain.description,
            imageUrl = domain.imageUrl,
            createdAt = domain.createdAt,
            edited = domain.edited
        )
    }
}
