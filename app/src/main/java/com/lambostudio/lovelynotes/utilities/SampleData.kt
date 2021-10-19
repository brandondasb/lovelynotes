package com.lambostudio.lovelynotes.utilities

import com.lambostudio.lovelynotes.database.NoteEntity
import java.util.*
import kotlin.collections.ArrayList

object SampleData {
    const val SAMPLE_TEXT_1: String = "1 eye open, when I am sleepingggg"
    const val SAMPLE_TEXT_2: String = "yeeeeaaaaah a\naa"
    const val SAMPLE_TEXT_3: String =
        "this note is about minding your own business" +
                " you should not be reading this yet here you are!!! long note long note long note long note long note long note long note long note long note "

    fun getDate(diff: Int): Date {
//        current date and time
//        each  time its called data object is generated
        val cal = GregorianCalendar()
        //change value suing the diff and adjust the value by a millisecond and return that
        cal.add(Calendar.MILLISECOND, diff)
        return cal.time
    }

    fun getNotes(): List<NoteEntity> {
        val notes: MutableList<NoteEntity> = ArrayList()
        notes.add(NoteEntity( date = getDate(0), text = SAMPLE_TEXT_1))
        notes.add(NoteEntity( date = getDate(-1),text = SAMPLE_TEXT_2))
        notes.add(NoteEntity( date = getDate(-2), text = SAMPLE_TEXT_3))
        return notes
    }
}