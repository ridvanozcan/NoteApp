package com.task.noteapp.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.task.noteapp.model.Note
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class NoteCrudTest {

    protected lateinit var db: DbManager

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        db = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            DbManager::class.java)
            .build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun addNoteTest() {
        val note = Note(
            0, "Title", "Note", "123456", -1,
            "", false, "www.deneme.com"
        )
        db.getNoteDao().addNote(note)
        val noteFromDb = db.getNoteDao().getNote()
        assertEquals(noteFromDb?.title, note.title)
    }

    @Test
    fun deleteNoteTest() {
        val note = Note(
            0, "Title", "Note", "123456", -1,
            "", false, "www.deneme.com"
        )
        db.getNoteDao().addNote(note)
        var noteFromDb = db.getNoteDao().getNote()
        db.getNoteDao().deleteNote(noteFromDb!!)
        noteFromDb = db.getNoteDao().getNote()
        assertNull(noteFromDb)
    }

    @Test
    fun updateNoteTest() {
        var note = Note(
            0, "Title", "Note", "123456", -1,
            "", false, "www.deneme.com"
        )
        db.getNoteDao().addNote(note)
        note.id = 1
        note.title = "UpdatedTitle"
        db.getNoteDao().updateNote(note)
        val noteFromDb = db.getNoteDao().getNote()
        assertEquals(noteFromDb?.title,"UpdatedTitle")
    }

    @Test
    fun getNoteAsLiveDataTest() {
        val note = Note(
            0, "Title", "Note", "123456", -1,
            "", false, "www.deneme.com"
        )
        db.getNoteDao().addNote(note)
        val noteWrappedInLiveData = db.getNoteDao().getAllNote()
        val noteFromDb = noteWrappedInLiveData.getValueBlocking()
        assertEquals(noteFromDb?.last()?.title, note.title)
    }
}

@Throws(InterruptedException::class)
fun <T> LiveData<T>.getValueBlocking(): T? {
    var value: T? = null
    val latch = CountDownLatch(1)
    val innerObserver = Observer<T> {
        value = it
        latch.countDown()
    }
    observeForever(innerObserver)
    latch.await(2, TimeUnit.SECONDS)
    return value
}

