package com.task.noteapp.di

import android.content.Context
import androidx.room.Room
import com.task.noteapp.db.DbManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DbManager {
        return Room.databaseBuilder(
            context.applicationContext,
            DbManager::class.java,
            "note_database"
        ).build()
    }
}
