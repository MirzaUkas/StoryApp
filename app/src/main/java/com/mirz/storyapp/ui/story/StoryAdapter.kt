package com.mirz.storyapp.ui.story

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.bumptech.glide.Glide
import com.mirz.storyapp.databinding.ItemStoryBinding
import com.mirz.storyapp.domain.entity.StoryEntity
import com.mirz.storyapp.ui.detail_story.StoryDetailActivity
import com.mirz.storyapp.utils.BaseAdapter
import com.mirz.storyapp.utils.DiffCallbackListener

class StoryAdapter : BaseAdapter<StoryEntity, ItemStoryBinding>(diffCallbackListener) {

    companion object {
        val diffCallbackListener = object : DiffCallbackListener<StoryEntity> {
            override fun areItemsTheSame(
                oldItem: StoryEntity, newItem: StoryEntity
            ) = oldItem.id == newItem.id
        }
    }

    override fun createViewHolder(inflater: LayoutInflater, container: ViewGroup) =
        ItemStoryBinding.inflate(inflater, container, false)

    override fun bind(binding: ItemStoryBinding, item: StoryEntity, position: Int, count: Int) {
        binding.tvItemName.text = item.name
        binding.tvItemDesc.text = item.description
        Glide.with(binding.root.context).load(item.photoUrl).into(binding.ivItemPhoto)
        binding.root.setOnClickListener {
            val optionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    binding.root.context as Activity,
                    Pair(binding.ivItemPhoto, "photo"),
                    Pair(binding.tvItemName, "name"),
                    Pair(binding.tvItemDesc, "description"),
                )

            binding.root.context.startActivity(
                Intent(
                    binding.root.context, StoryDetailActivity::class.java
                ).putExtra(StoryDetailActivity.EXTRA_STORY_ID, item.id), optionsCompat.toBundle()
            )
        }
    }
}