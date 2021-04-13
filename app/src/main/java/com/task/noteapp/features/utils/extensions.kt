package com.task.noteapp.features.utils

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.R


@SuppressLint("UseCompatLoadingForDrawables")
fun RecyclerView.initRecyclerViewWithLineDecoration(context: Context) {
    val itemDecoration =
        DividerItemDecoration(context, LinearLayoutManager(context).orientation).apply {
            setDrawable(context.getDrawable(R.drawable.line)!!)
        }
    addItemDecoration(itemDecoration)
}
