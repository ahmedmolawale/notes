package com.task.noteapp.features.notes.ui.addnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.task.noteapp.R
import com.task.noteapp.databinding.AddNoteFragmentBinding
import com.task.noteapp.features.notes.ui.MainActivity
import com.task.noteapp.features.utils.EventObserver
import com.task.noteapp.features.utils.setupSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AddNoteFragment : Fragment() {

    private var _binding: AddNoteFragmentBinding? = null
    private val binding get() = _binding!!
    private val addNoteViewModel: AddNoteViewModel by viewModels()
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
        (activity as MainActivity).supportActionBar?.title = getString(R.string.new_note)
        observeChanges()
    }

    private fun observeChanges() {
        addNoteViewModel.noteSaveView.observe(viewLifecycleOwner, EventObserver {
            it.noteSaved?.let {
                binding.addNoteTitleEditText.setText("")
                binding.addNoteDescriptionEditText.setText("")
                binding.addNoteImageUrlEditText.setText("")
            }
            it.message?.let { id ->
                view?.setupSnackbar(id, Snackbar.LENGTH_LONG)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
