package com.lambostudio.lovelynotes.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    /* this is enough to gen sql scripts done by room library*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(noteEntity: NoteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertALLNotes(notes: List<NoteEntity>)

    @Delete
    fun deleteNote(noteEntity: NoteEntity)

    /** specifying queries to run, we can use the live data object
    when we need to expose data to the presenting layer of the application **/

    @Query("SELECT * FROM  NOTES WHERE id= :id")
    fun getNoteByID(id: Int): NoteEntity

    @Query("SELECT * FROM  NOTES ORDER BY date DESC")
    fun getAll(): LiveData<List<NoteEntity>>

    @Query("DELETE FROM notes")
    fun deleteAll(): Int

    @Query("SELECT COUNT(*) FROM notes")
    fun getCount(): Int


}