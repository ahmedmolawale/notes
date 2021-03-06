package com.task.noteapp.features.notes.ui.editnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.task.noteapp.R
import com.task.noteapp.databinding.EditNoteFragmentBinding
import com.task.noteapp.features.notes.ui.MainActivity
import com.task.noteapp.features.utils.EventObserver
import com.task.noteapp.features.utils.setupSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class EditNoteFragment : Fragment() {

    private var _binding: EditNoteFragmentBinding? = null
    private val binding get() = _binding!!
    private val editNoteViewModel: EditNoteViewModel by viewModels()
    private val args: EditNoteFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EditNoteFragmentBinding.inflate(inflater, container, false)
        binding.viewmodel = editNoteViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editNoteViewModel.setNote(args.note)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.menu_edit_note)
        observeChanges()
    }

    private fun observeChanges() {
        editNoteViewModel.noteEditView.observe(
            viewLifecycleOwner,
            EventObserver {
                it.message?.let { id ->
                    view?.setupSnackbar(id, Snackbar.LENGTH_SHORT)
                }
                it.updated?.let {
                    findNavController().popBackStack(R.id.noteFragment, false)
                }
            }
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_update -> {
                editNoteViewModel.updateNote()
                true
            }
            else -> false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_edit_fragment_menu, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
