package com.lambostudio.lovelynotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.lambostudio.lovelynotes.utilities.SampleData

class HomeViewModel(application: Application): AndroidViewModel(application) {
val mNotes = SampleData.getNotes()
}