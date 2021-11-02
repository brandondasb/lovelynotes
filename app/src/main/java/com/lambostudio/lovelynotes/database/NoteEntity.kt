package com.lambostudio.lovelynotes.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")// room related
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)// only auto generate for ints
    val id: Int? = null,
    val date: Date?,
    var text: String?
)
//room can only use 1 constructor., if you have more constructor mark them with ignore