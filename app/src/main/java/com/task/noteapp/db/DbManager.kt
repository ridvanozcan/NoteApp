package com.task.noteapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.task.noteapp.model.Note


@Database(entities = [Note::class], version = 1)
abstract class DbManager : RoomDatabase() {
    abstract fun getNoteDao(): DAO
}