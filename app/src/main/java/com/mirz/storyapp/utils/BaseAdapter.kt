package com.mirz.storyapp.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T, V : ViewBinding>(private val diffUtilCallbackListener: DiffCallbackListener<T>) :
    RecyclerView.Adapter<BaseAdapter.BaseViewHolder>() {
    private var _items = mutableListOf<T>()

    fun setItems(items: List<T>) {
        val diffResult =
            DiffUtil.calculateDiff(DiffUtilCallback(_items, items.toMutableList(), diffUtilCallbackListener))
        _items = items.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }
    fun getItems() = _items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(createViewHolder(LayoutInflater.from(parent.context), parent))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        bind(holder.binding as V, _items[position], position, _items.size)
    }

    override fun getItemCount() = _items.size

    abstract fun createViewHolder(inflater: LayoutInflater, container: ViewGroup): ViewBinding

    abstract fun bind(binding: V, item: T, position: Int, count: Int)

    class BaseViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)

}