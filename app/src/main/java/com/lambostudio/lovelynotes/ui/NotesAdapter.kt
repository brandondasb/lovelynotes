package com.lambostudio.lovelynotes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.lambostudio.lovelynotes.HomeFragment
import com.lambostudio.lovelynotes.R
import com.lambostudio.lovelynotes.database.NoteEntity
import com.lambostudio.lovelynotes.databinding.NoteListItemBinding

class NotesAdapter(mNotes: List<NoteEntity>, private val homeFragment: HomeFragment) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private val mNotes: List<NoteEntity> = mNotes


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NoteListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note: NoteEntity = mNotes[position]
        holder.mTextView.text = note.text
        homeFragment.launchNote(holder.mTextView,note)
    }

    override fun getItemCount(): Int = mNotes.size

    class ViewHolder(binding: NoteListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val mTextView = binding.noteText
    }
}