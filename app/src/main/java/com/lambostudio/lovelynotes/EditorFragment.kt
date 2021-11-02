package com.lambostudio.lovelynotes

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lambostudio.lovelynotes.database.NoteEntity
import com.lambostudio.lovelynotes.databinding.FragmentEditorBinding
import com.lambostudio.lovelynotes.utilities.Constants
import com.lambostudio.lovelynotes.viewmodel.EditorViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class EditorFragment : Fragment() {

    private var mNewNote: Boolean = false
    private var _binding: FragmentEditorBinding? = null
    lateinit var editorViewModel: EditorViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
//        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
//        activity?.actionBar?.setHomeAsUpIndicator(R.drawable.ic_check)
        _binding = FragmentEditorBinding.inflate(inflater, container, false)
//        initViewModel()
        return binding.root
    }

    private fun initViewModel() {
        editorViewModel = ViewModelProvider(this)[EditorViewModel::class.java]
        binding.contentEditorInclude.noteText.setOnFocusChangeListener { view, isFocused ->
            if (!isFocused) {
                saveAndReturn()
            }
        }
        val noteObserver: Observer<NoteEntity> = Observer { noteEntity ->
            if (noteEntity != null) {
                binding.contentEditorInclude.noteText.setText(noteEntity.text)
            } else {

            }
        }

        editorViewModel.mLiveNote.observe(viewLifecycleOwner, noteObserver)

        val extras = arguments?.getInt(Constants.NOTE_ID_KEY)
        if (arguments?.getString(Constants.EDIT_NOTE) == Constants.NEW_NOTE) {
            mNewNote = true
        } else {
            activity?.title = arguments?.getString(Constants.EDIT_NOTE)
            if (extras != null) {
                val noteId: Int = extras
                editorViewModel.loadData(noteId)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
////            this.handleOnBackPressed()
//            saveAndReturn()
//
//        }
        initViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (!mNewNote) {
            inflater.inflate(R.menu.menu_editor, menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> {
                saveAndReturn()
                true
            }
            R.id.action_delete -> {
                editorViewModel.deleteNote()
                activity?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPause() {
        super.onPause()
//        saveAndReturn()
    }

    private fun saveAndReturn() {
        editorViewModel.saveNote(binding.contentEditorInclude.noteText.text.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}