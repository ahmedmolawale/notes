package com.task.noteapp.features.notes.ui.notelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.task.noteapp.R
import com.task.noteapp.databinding.NoteFragmentBinding
import com.task.noteapp.features.notes.model.NotePresentation
import com.task.noteapp.features.notes.ui.MainActivity
import com.task.noteapp.features.notes.ui.notelist.adapter.NotesAdapter
import com.task.noteapp.features.utils.EventObserver
import com.task.noteapp.features.utils.initRecyclerViewWithLineDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class NoteListFragment : Fragment() {

    private var _binding: NoteFragmentBinding? = null
    private val binding get() = _binding!!
    private val noteListViewModel: NoteListViewModel by viewModels()
    private lateinit var notesAdapter: NotesAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NoteFragmentBinding.inflate(inflater, container, false)
        binding.noteViewModel = noteListViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteListViewModel.fetchNotes()
        (activity as MainActivity).supportActionBar?.title = getString(R.string.notes)
        setupListAdapter()
        observeChanges()
    }

    private fun openNoteDetails(note: NotePresentation) {
        val action = NoteListFragmentDirections
            .actionNoteFragmentToNoteDetailFragment(note)
        findNavController().navigate(action)
    }

    private fun setupListAdapter() {
        notesAdapter =
            NotesAdapter { note -> openNoteDetails(note) }
        context?.let {
            binding.noteList.initRecyclerViewWithLineDecoration(it)
        }
        binding.noteList.adapter = notesAdapter
    }

    private fun observeChanges() {
        noteListViewModel.newNoteEvent.observe(viewLifecycleOwner, EventObserver {
            navigateToAddNewNote()
        })
        noteListViewModel.noteListView.observe(viewLifecycleOwner, {
            if (it.isEmpty) {
                binding.errorState.visibility = View.VISIBLE
                binding.errorState.setCaption(getString(R.string.no_note))
                binding.errorState.isButtonVisible = false
            }
            if (it.errorMessage != null) {
                binding.errorState.visibility = View.VISIBLE
                binding.errorState.setCaption(getString(it.errorMessage))
                binding.errorState.isButtonVisible = true
                binding.errorState.onRetry {
                    binding.errorState.visibility = View.GONE
                    noteListViewModel.fetchNotes()
                }
            }
        })
    }

    private fun navigateToAddNewNote() {
        val action = NoteListFragmentDirections
            .actionNoteFragmentToAddNoteFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
