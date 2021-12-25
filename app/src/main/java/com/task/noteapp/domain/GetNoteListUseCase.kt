package com.task.noteapp.domain

import com.task.noteapp.db.repository.NoteRepository
import com.task.noteapp.model.Note
import javax.inject.Inject

class GetNoteListUseCase @Inject constructor(
    private val repository: NoteRepository,
) {
    fun getNote() = repository.getNoteList()

    suspend fun addNote(note: Note) = repository.setNote(note)

    suspend fun deleteNote(note: Note) = repository.deleteNote(note)

    suspend fun updateNote(note: Note) = repository.updateNote(note)

    fun searchNote(query: String) = repository.searchNote(query)
}