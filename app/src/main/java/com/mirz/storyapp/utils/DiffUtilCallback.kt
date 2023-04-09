package com.mirz.storyapp.utils

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback<T>(
    private val old: List<T>,
    private val new: List<T>,
    private val listener: DiffCallbackListener<T>
) : DiffUtil.Callback() {
    override fun getOldListSize() = old.size

    override fun getNewListSize() = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        listener.areItemsTheSame(old[oldItemPosition], new[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        old[oldItemPosition] == new[newItemPosition]
}

interface DiffCallbackListener<T> {
    fun areItemsTheSame(oldItem: T, newItem: T): Boolean
}