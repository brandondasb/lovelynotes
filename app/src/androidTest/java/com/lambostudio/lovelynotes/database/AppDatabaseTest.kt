package com.lambostudio.lovelynotes.database

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lambostudio.lovelynotes.utilities.SampleData
import junit.framework.TestCase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    val TAG: String = "Junit"
    lateinit var mDao: NoteDao
    lateinit var mDb: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        mDb = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()

        mDao = mDb.NoteDao()
        Log.i(TAG, "createDb")
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        mDb.close()
        Log.i(TAG, "CloseDb")
    }

    @Test
    @Throws(Exception::class)
    fun createAndRetrieveNotes() {
        mDao.insertALLNotes(SampleData.getNotes())
        val count = mDao.getCount()
        Log.i(TAG, "createAndRetrieveNote= $count")

        assertEquals(SampleData.getNotes().size, count)
    }

    @Test
    @Throws(Exception::class)
    fun compareString() {
        mDao.insertALLNotes(SampleData.getNotes())
        val original  =SampleData.getNotes()[1]
        val fromDb = mDao.getNoteByID(1)
        Log.i(TAG, "createAndRetrieveNote= $fromDb")

        assertEquals(fromDb.text, original.text)
        assertEquals(1,fromDb.id)
    }


}