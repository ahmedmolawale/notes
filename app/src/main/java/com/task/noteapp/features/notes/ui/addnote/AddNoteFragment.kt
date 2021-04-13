package com.task.noteapp.features.notes.ui.addnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.task.noteapp.databinding.AddNoteFragmentBinding
import com.task.noteapp.databinding.EditNoteFragmentBinding
import com.task.noteapp.features.notes.model.NotePresentation
import com.task.noteapp.features.notes.ui.notelist.adapter.NotesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AddNoteFragment : Fragment() {

    private var _binding: AddNoteFragmentBinding? = null
    private val binding get() = _binding!!
    private val addNoteViewModel: AddNoteViewModel by viewModels()
    private lateinit var notesAdapter: NotesAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddNoteFragmentBinding.inflate(inflater, container, false)
        binding.viewmodel = addNoteViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //noteListViewModel.fetchNotes()
        //setupListAdapter()
    }

    private fun openNoteDetails(note: NotePresentation) {
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
