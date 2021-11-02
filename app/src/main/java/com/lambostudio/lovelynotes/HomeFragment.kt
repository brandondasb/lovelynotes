package com.lambostudio.lovelynotes

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lambostudio.lovelynotes.database.NoteEntity
import com.lambostudio.lovelynotes.databinding.FragmentHomeBinding
import com.lambostudio.lovelynotes.ui.NotesAdapter
import com.lambostudio.lovelynotes.utilities.Constants
import com.lambostudio.lovelynotes.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var notesData: MutableList<NoteEntity> = ArrayList()
    private lateinit var homeViewModel: HomeViewModel

    private lateinit var mAdapter: NotesAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        mAdapter = NotesAdapter(notesData, this)
        initRecyclerView()
        initViewModel()


        return binding.root
    }

    private fun initViewModel() {
        val notesObserver: Observer<List<NoteEntity>> = Observer<List<NoteEntity>> { noteEntities ->
            notesData.clear()
            notesData.addAll(noteEntities)

            mAdapter = NotesAdapter(notesData, this)
            binding.recyclerView.adapter = mAdapter
            mAdapter.notifyDataSetChanged()

        }
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        homeViewModel.mNotes.observe(viewLifecycleOwner, notesObserver)
    }

    private fun initRecyclerView() {
        binding.recyclerView.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = linearLayoutManager
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingActionButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(Constants.EDIT_NOTE, Constants.NEW_NOTE)
            findNavController().navigate(R.id.action_HomeFragment_to_EditorFragment, bundle)
        }

//        binding.recyclerView.setOnClickListener {
//            findNavController().navigate(R.id.action_HomeFragment_to_EditorFragment)
//        }
    }

    fun launchNote(noteView: View, note: NoteEntity) {
        noteView.setOnClickListener { selectedNote ->
            val bundle = Bundle()
            note.id?.let {
                bundle.putInt(Constants.NOTE_ID_KEY, it)
                bundle.putString(Constants.EDIT_NOTE, Constants.EDIT_NOTE)
            }
            val intent = Intent(requireContext(), EditorFragment::class.java)
            intent.putExtras(bundle)
            findNavController().navigate(R.id.action_HomeFragment_to_EditorFragment, bundle)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return when (item.itemId) {
            R.id.action_add_sample_data -> {
                addSampleData()
                true
            }
            R.id.action_delete_all -> {
                deleteAllNotes()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteAllNotes() {
        homeViewModel.deleteAllNotes()
    }

    private fun addSampleData() {
//        viewModelScope.launch {
//            (context as CoroutineScope).launch {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            homeViewModel.addSampleData()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}