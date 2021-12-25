package com.task.noteapp.ui.list

import androidx.lifecycle.LiveData
import com.task.noteapp.core.viewmodel.BaseViewModel
import com.task.noteapp.domain.GetNoteListUseCase
import com.task.noteapp.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val useCase: GetNoteListUseCase
) : BaseViewModel() {

    fun getAllNotes(): LiveData<List<Note>> = useCase.getNote()

    fun searchNote(query: String): LiveData<List<Note>> = useCase.searchNote(query)

}