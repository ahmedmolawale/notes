package com.task.noteapp.features.notes.ui.notedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.task.noteapp.databinding.NoteDetailFragmentBinding
import com.task.noteapp.features.utils.EventObserver
import com.task.noteapp.features.utils.setupSnackbar
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
        observeChanges()
    }

    private fun observeChanges() {
        noteDetailViewModel.noteDetailView.observe(viewLifecycleOwner, EventObserver {
            it.editNote?.let {
                val action = NoteDetailFragmentDirections
                    .actionNoteDetailToEditNoteFragment(args.note)
                findNavController().navigate(action)
            }
            it.message?.let { id ->
                view?.setupSnackbar(id, Snackbar.LENGTH_SHORT)
            }
            it.deleted?.let {
                val action = NoteDetailFragmentDirections
                    .actionNoteDetailToNoteFragment()
                findNavController().navigate(action)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
