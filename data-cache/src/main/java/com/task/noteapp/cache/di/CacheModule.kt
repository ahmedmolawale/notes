package com.task.noteapp.cache.di

import android.content.Context
import androidx.room.Room
import com.task.noteapp.cache.room.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CacheModule {

    companion object {
        @Singleton
        @Provides
        fun provideDataBase(@ApplicationContext context: Context): NoteDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                NoteDatabase.DB_NAME
            ).fallbackToDestructiveMigration().build()
        }

        @Singleton
        @Provides
        fun provideNotesDao(noteDatabase: NoteDatabase) = noteDatabase.notesDao
    }

}


