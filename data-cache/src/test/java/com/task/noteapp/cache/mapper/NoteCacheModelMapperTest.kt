package com.task.noteapp.cache.mapper

import com.google.common.truth.Truth.assertThat
import com.task.noteapp.cache.entity.DummyData
import com.task.noteapp.cache.entity.NoteCacheModel
import com.task.noteapp.data.model.NoteEntity
import org.junit.Test

class NoteCacheModelMapperTest {
    private val noteCacheModelMapper: NoteCacheModelMapper = NoteCacheModelMapper()

    @Test
    fun `check that mapToModel returns correct data`() {
        val noteEntity: NoteEntity = DummyData.noteEntity
        val noteCacheModel: NoteCacheModel = noteCacheModelMapper.mapToModel(noteEntity)
        assertThat(noteCacheModel.id).isEqualTo(noteEntity.id)
        assertThat(noteCacheModel.title).isEqualTo(noteEntity.title)
        assertThat(noteCacheModel.description).isEqualTo(noteEntity.description)
        assertThat(noteCacheModel.imageUrl).isEqualTo(noteEntity.imageUrl)
        assertThat(noteCacheModel.edited).isEqualTo(noteEntity.edited)
        assertThat(noteCacheModel.createdAt).isEqualTo(noteEntity.createdAt)
    }

    @Test
    fun `check that mapToEntity returns correct data`() {
        val noteCacheModel: NoteCacheModel = DummyData.noteCacheModel
        val noteEntity: NoteEntity = noteCacheModelMapper.mapToEntity(noteCacheModel)
        assertThat(noteCacheModel.id).isEqualTo(noteEntity.id)
        assertThat(noteCacheModel.title).isEqualTo(noteEntity.title)
        assertThat(noteCacheModel.description).isEqualTo(noteEntity.description)
        assertThat(noteCacheModel.imageUrl).isEqualTo(noteEntity.imageUrl)
        assertThat(noteCacheModel.edited).isEqualTo(noteEntity.edited)
        assertThat(noteCacheModel.createdAt).isEqualTo(noteEntity.createdAt)
    }

    @Test
    fun `check that mapToEntityList returns correct data`() {
        val noteCacheModelList: List<NoteCacheModel> = listOf(DummyData.noteCacheModel)
        val noteEntityList: List<NoteEntity> =
            noteCacheModelMapper.mapToEntityList(noteCacheModelList)
        assertThat(noteEntityList).isNotEmpty()
        assertThat(noteEntityList).hasSize(noteCacheModelList.size)
        val noteCacheModel = noteCacheModelList[0]
        val noteEntity = noteEntityList[0]
        assertThat(noteCacheModel.id).isEqualTo(noteEntity.id)
        assertThat(noteCacheModel.title).isEqualTo(noteEntity.title)
        assertThat(noteCacheModel.description).isEqualTo(noteEntity.description)
        assertThat(noteCacheModel.imageUrl).isEqualTo(noteEntity.imageUrl)
        assertThat(noteCacheModel.edited).isEqualTo(noteEntity.edited)
        assertThat(noteCacheModel.createdAt).isEqualTo(noteEntity.createdAt)
    }

    @Test
    fun `check that mapToModelList returns correct data`() {
        val noteEntityList: List<NoteEntity> = listOf(DummyData.noteEntity)
        val noteCacheModelList: List<NoteCacheModel> =
            noteCacheModelMapper.mapToModelList(noteEntityList)
        assertThat(noteCacheModelList).isNotEmpty()
        assertThat(noteCacheModelList).hasSize(noteEntityList.size)
        val noteCacheModel = noteCacheModelList[0]
        val noteEntity = noteEntityList[0]
        assertThat(noteCacheModel.id).isEqualTo(noteEntity.id)
        assertThat(noteCacheModel.title).isEqualTo(noteEntity.title)
        assertThat(noteCacheModel.description).isEqualTo(noteEntity.description)
        assertThat(noteCacheModel.imageUrl).isEqualTo(noteEntity.imageUrl)
        assertThat(noteCacheModel.edited).isEqualTo(noteEntity.edited)
        assertThat(noteCacheModel.createdAt).isEqualTo(noteEntity.createdAt)
    }
}