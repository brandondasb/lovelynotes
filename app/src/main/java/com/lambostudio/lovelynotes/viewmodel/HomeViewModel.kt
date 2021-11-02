package com.lambostudio.lovelynotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.lambostudio.lovelynotes.database.AppRepository
import com.lambostudio.lovelynotes.database.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository: AppRepository = AppRepository(application)
    val mNotes: LiveData<List<NoteEntity>> = mRepository.mNotes
    fun addSampleData() {
//        val mNotes = mRepository.mNotes
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.addSampleData()
        }
    }

    fun deleteAllNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.deleteAllNotes()
        }
    }
}