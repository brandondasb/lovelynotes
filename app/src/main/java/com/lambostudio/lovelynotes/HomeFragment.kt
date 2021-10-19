package com.lambostudio.lovelynotes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lambostudio.lovelynotes.database.NoteEntity
import com.lambostudio.lovelynotes.databinding.FragmentHomeBinding
import com.lambostudio.lovelynotes.ui.NotesAdapter
import com.lambostudio.lovelynotes.utilities.SampleData
import com.lambostudio.lovelynotes.viewmodel.HomeViewModel

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

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        initViewModel()
        initRecyclerView()
        notesData.addAll(SampleData.getNotes())
        for (note in notesData) {

            Log.i("plainotes", note.toString())
        }

        return binding.root
    }

    private fun initViewModel() {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    private fun initRecyclerView() {
        binding.recyclerView.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = linearLayoutManager

        mAdapter = NotesAdapter(homeViewModel.mNotes)
        binding.recyclerView.adapter = mAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingActionButton.setOnClickListener { view ->
            findNavController().navigate(R.id.action_HomeFragment_to_SecondFragment)
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
        }
        binding.recyclerView.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}