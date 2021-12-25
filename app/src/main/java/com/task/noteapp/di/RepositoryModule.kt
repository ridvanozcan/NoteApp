package com.task.noteapp.di

import com.task.noteapp.db.repository.NoteRepository
import com.task.noteapp.db.repository.NoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun mediaRepository(noteRepositoryImpl: NoteRepositoryImpl): NoteRepository
}