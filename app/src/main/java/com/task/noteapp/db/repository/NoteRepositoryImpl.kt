package com.task.noteapp.db.repository


import androidx.lifecycle.LiveData
import androidx.room.withTransaction
import com.task.noteapp.db.DbManager
import com.task.noteapp.model.Note
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val dbManager: DbManager
) : NoteRepository {

    override fun getNoteList(): LiveData<List<Note>> {
        return dbManager.getNoteDao().getAllNote()
    }

    override suspend fun setNote(note: Note) {
        dbManager.withTransaction {
            dbManager.getNoteDao().addNote(note)
        }
    }

    override suspend fun deleteNote(note: Note) {
        dbManager.withTransaction {
            dbManager.getNoteDao().deleteNote(note)
        }
    }

    override suspend fun updateNote(note: Note) {
        dbManager.withTransaction {
            dbManager.getNoteDao().updateNote(note)
        }
    }

    override fun searchNote(query: String): LiveData<List<Note>> {
        return dbManager.getNoteDao().searchNote(query)
    }
}