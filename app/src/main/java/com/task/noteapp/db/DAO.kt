package com.task.noteapp.db

import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.room.*
import com.task.noteapp.model.Note

@Dao
interface DAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNote(note: Note)

    @Query("SELECT * FROM Note ORDER BY id DESC")
    fun getAllNote(): LiveData<List<Note>>

    @Query("SELECT * FROM Note")
    @Nullable
    fun getNote(): Note?

    @Delete
    fun deleteNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Query("SELECT * FROM Note WHERE title LIKE :query OR content LIKE :query OR date LIKE :query ORDER BY id DESC")
    fun searchNote(query: String): LiveData<List<Note>>
}