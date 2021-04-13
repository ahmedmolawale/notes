package com.task.noteapp.features.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.task.noteapp.R


/**
 * Transforms static java function Snackbar.make() to an extension function on View.
 */
fun View.showSnackbar(snackbarText: String, timeLength: Int) {
    Snackbar.make(this, snackbarText, timeLength).run {
        show()
    }
}

/**
 * Triggers a snackbar message when the value contained by note view message is modified.
 */
fun View.setupSnackbar(
    stringId: Int,
    timeLength: Int
) {
    showSnackbar(context.getString(stringId), timeLength)
}

@SuppressLint("UseCompatLoadingForDrawables")
fun RecyclerView.initRecyclerViewWithLineDecoration(context: Context) {
    val itemDecoration =
        DividerItemDecoration(context, LinearLayoutManager(context).orientation).apply {
            setDrawable(context.getDrawable(R.drawable.line)!!)
        }
    addItemDecoration(itemDecoration)
}
