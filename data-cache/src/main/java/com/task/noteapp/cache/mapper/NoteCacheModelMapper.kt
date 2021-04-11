package com.task.noteapp.cache.mapper

import com.task.noteapp.cache.entity.NoteCacheModel
import com.task.noteapp.cache.mapper.base.CacheModelMapper
import com.task.noteapp.data.model.NoteEntity

class NoteCacheModelMapper : CacheModelMapper<NoteCacheModel, NoteEntity> {

    override fun mapToModel(entity: NoteEntity): NoteCacheModel {
        return NoteCacheModel(
            id = entity.id,
            title = entity.title,
            description = entity.description,
            imageUrl = entity.imageUrl,
            createdAt = entity.createdAt,
            edited = entity.edited
        )
    }

    override fun mapToEntity(model: NoteCacheModel): NoteEntity {
        return NoteEntity(
            id = model.id,
            title = model.title,
            description = model.description,
            imageUrl = model.imageUrl,
            createdAt = model.createdAt,
            edited = model.edited
        )
    }
}