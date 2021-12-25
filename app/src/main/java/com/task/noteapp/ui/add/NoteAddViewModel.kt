package com.task.noteapp.ui.add

import androidx.lifecycle.viewModelScope
import com.task.noteapp.core.viewmodel.BaseViewModel
import com.task.noteapp.domain.GetNoteListUseCase
import com.task.noteapp.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@HiltViewModel
class NoteAddViewModel @Inject constructor(
    private val useCase: GetNoteListUseCase
) : BaseViewModel() {

    private var imagePath: String? = ""
    private val currentDate = SimpleDateFormat.getDateInstance().format(Date())

    fun saveNote(newNote: Note) {
        if (newNote.id == 0) {
            viewModelScope.launch {
                useCase
                    .addNote(newNote)
            }
        } else {
            updateNote(newNote)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            useCase.deleteNote(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            useCase.updateNote(note)
        }
    }

    fun saveImagePath(path: String?) {
        imagePath = path
    }

    fun getImagePath(): String? {
        return if(imagePath != null) imagePath else ""
    }

    fun getCurrentDate(): String? {
        return currentDate.toString()
    }
}