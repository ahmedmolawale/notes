package com.task.noteapp.features.notes.ui.notedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.task.noteapp.databinding.NoteDetailFragmentBinding
import com.task.noteapp.features.notes.ui.notelist.adapter.NotesAdapter
import com.task.noteapp.features.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class NoteDetailFragment : Fragment() {

    private var _binding: NoteDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val noteDetailViewModel: NoteDetailViewModel by viewModels()
    private val args: NoteDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NoteDetailFragmentBinding.inflate(inflater, container, false)
        binding.viewmodel = noteDetailViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteDetailViewModel.setNote(args.note)
        setupNavigation()
    }

    private fun setupNavigation() {
        noteDetailViewModel.editNoteEvent.observe(viewLifecycleOwner, EventObserver {
            val action = NoteDetailFragmentDirections
                .actionNoteDetailToEditNoteFragment(args.note)
            findNavController().navigate(action)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
