package com.task.noteapp.features.notes.di

import com.task.noteapp.data.repository.NoteRepositoryImpl
import com.task.noteapp.domain.repository.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class NoteModule {
    @Binds
    abstract fun bindNoteRepository(repo: NoteRepositoryImpl): NoteRepository
}