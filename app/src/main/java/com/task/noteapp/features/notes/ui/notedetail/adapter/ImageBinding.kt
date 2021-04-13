package com.task.noteapp.features.notes.ui.notedetail.adapter

import androidx.databinding.BindingAdapter
import coil.load
import com.google.android.material.imageview.ShapeableImageView
import com.task.noteapp.R
import com.task.noteapp.features.notes.model.NotePresentation

/**
 * [BindingAdapter]s for the [NotePresentation]s list.
 */
@BindingAdapter("imageUrl")
fun setImage(imageView: ShapeableImageView, imageUrl: String?) {
    imageUrl?.let {
        imageView.load(imageUrl) {
            placeholder(R.drawable.ic_empty_image)
            error(R.drawable.ic_empty_image)
        }
    }
}