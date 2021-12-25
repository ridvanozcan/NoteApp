package com.task.noteapp.db.repository

import androidx.lifecycle.LiveData
import com.task.noteapp.model.Note

interface NoteRepository {

    fun getNoteList(): LiveData<List<Note>>

    suspend fun setNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun updateNote(note: Note)

    fun searchNote(query: String): LiveData<List<Note>>
}