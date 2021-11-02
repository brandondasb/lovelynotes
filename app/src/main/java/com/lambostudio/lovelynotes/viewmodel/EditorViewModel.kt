package com.lambostudio.lovelynotes.viewmodel

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lambostudio.lovelynotes.database.AppRepository
import com.lambostudio.lovelynotes.database.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class EditorViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository: AppRepository = AppRepository(application)
    val mLiveNote: MutableLiveData<NoteEntity> = MutableLiveData()

    fun loadData(noteId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val note: NoteEntity = mRepository.getNoteById(noteId)
            mLiveNote.postValue(note)
            // post value get the observe to trigger on on change method and
            //update the result
        }
    }

    fun saveNote(noteText: String) {
        var note: NoteEntity? = mLiveNote.value
        if (note == null) {
            if (TextUtils.isEmpty(noteText.trim())) {

            } else {
                note = NoteEntity(date = Date(), text = noteText.trim())
            }
        } else {
            note.text = noteText.trim()
        }
        viewModelScope.launch(Dispatchers.IO) {
            if (note != null) {
                mRepository.insertNote(note)
            }
        }
    }

    fun deleteNote() {
        viewModelScope.launch(Dispatchers.IO) {
            mLiveNote.value?.let { mRepository.deleteNote(it) }
        }
    }

}