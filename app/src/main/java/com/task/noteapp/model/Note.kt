package com.task.noteapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String,
    val content: String,
    val date: String,
    var color: Int = -1,
    val imagePath: String?,
    var edit: Boolean,
    val url: String?
) : Serializable