package com.lambostudio.lovelynotes.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lambostudio.lovelynotes.databinding.NoteListItemBinding
import com.lambostudio.lovelynotes.database.NoteEntity

class NotesAdapter(mNotes: List<NoteEntity>) :
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
    }

    override fun getItemCount(): Int = mNotes.size

    class ViewHolder(binding: NoteListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val mTextView = binding.noteText
    }
}