package com.task.noteapp.features.notes.ui.notelist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.task.noteapp.R
import com.task.noteapp.databinding.NoteItemBinding
import com.task.noteapp.features.notes.model.NotePresentation

/**
 * Adapter for the [NotePresentation]'s list.
 */

typealias NoteClickListener = (NotePresentation) -> Unit

class NotesAdapter(private val onClick: NoteClickListener) :
    ListAdapter<NotePresentation, NotesAdapter.ViewHolder>(
        CharacterDiffCallback()
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(onClick, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    class ViewHolder private constructor(private val binding: NoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(onClick: NoteClickListener, item: NotePresentation) {
            binding.root.setOnClickListener {
                onClick(item)
            }
            binding.note = item
            binding.noteImage.load(item.imageUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_empty_image)
                error(R.drawable.ic_empty_image)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NoteItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(
                    binding
                )
            }
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class CharacterDiffCallback : DiffUtil.ItemCallback<NotePresentation>() {
    override fun areItemsTheSame(
        oldItem: NotePresentation,
        newItem: NotePresentation
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: NotePresentation,
        newItem: NotePresentation
    ): Boolean {
        return oldItem == newItem
    }
}

/**
 * [BindingAdapter]s for the [NotePresentation]s list.
 */
@BindingAdapter("items")
fun setItems(listView: RecyclerView, notes: List<NotePresentation>?) {
    notes?.let {
        (listView.adapter as NotesAdapter).submitList(notes)
    }
}
