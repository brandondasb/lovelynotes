package com.lambostudio.lovelynotes.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.lambostudio.lovelynotes.utilities.SampleData


object AppRepository {
    private lateinit var localDataStorage: AppDatabase
    //        private lateinit var networkDataStorage:
    lateinit var mNotes: LiveData<List<NoteEntity>>

    /**
     * An interesting feature of the Kotlin language is the ability to define an "invoke operator".
     * When you specify an invoke operator on a class, it can be called on any instances
     * of the class without a method name!This trick seems especially useful for classes
     * that really only have one method to be used.
     * **/
    operator fun invoke(context: Context): AppRepository {
        localDataStorage = AppDatabase.getInstance(context)
        mNotes = getAllNotes()
        return this
    }

    //all Room query must be done in background thread
    fun addSampleData() {
        localDataStorage.NoteDao().insertALLNotes(SampleData.getNotes())
    }

    private fun getAllNotes(): LiveData<List<NoteEntity>> {
        return localDataStorage.NoteDao().getAll()
    }

    fun deleteAllNotes() {
        localDataStorage.NoteDao().deleteAll()
    }

    fun getNoteById(noteId: Int): NoteEntity = localDataStorage.NoteDao().getNoteById(noteId)
    fun insertNote(note: NoteEntity) {
        localDataStorage.NoteDao().insertNote(note)
    }

    fun deleteNote(note: NoteEntity) {
        localDataStorage.NoteDao().deleteNote(note)
    }
}