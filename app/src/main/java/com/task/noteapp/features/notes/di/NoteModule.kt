package com.task.noteapp.features.notes.di

import com.task.noteapp.cache.cacheImpl.NoteCacheImpl
import com.task.noteapp.data.contract.cache.NoteCache
import com.task.noteapp.data.repository.NoteRepositoryImpl
import com.task.noteapp.domain.repository.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NoteModule {
    @Binds
    abstract fun bindNoteRepository(repo: NoteRepositoryImpl): NoteRepository

    @Binds
    abstract fun bindNoteCache(repo: NoteCacheImpl): NoteCache
}