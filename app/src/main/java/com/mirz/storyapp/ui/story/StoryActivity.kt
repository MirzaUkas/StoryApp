package com.mirz.storyapp.ui.story

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mirz.storyapp.Locator
import com.mirz.storyapp.databinding.ActivityStoryBinding
import com.mirz.storyapp.ui.adapter.LoadingStateAdapter
import com.mirz.storyapp.ui.adapter.StoryAdapter
import com.mirz.storyapp.ui.add_story.AddStoryActivity
import com.mirz.storyapp.ui.login.LoginActivity
import com.mirz.storyapp.ui.maps.MapsActivity
import com.mirz.storyapp.utils.launchAndCollectIn

class StoryActivity : AppCompatActivity() {
    private val binding by lazy { ActivityStoryBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<StoryViewModel>(factoryProducer = { Locator.storyViewModelFactory })
    private val adapter by lazy { StoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initAdapter()
        viewModel.storyState.launchAndCollectIn(this) {
            adapter.submitData(lifecycle, it.resultStories)
            binding.tvName.text = it.username
        }

        binding.fabAddStory.setOnClickListener {
            startActivity(Intent(this@StoryActivity, AddStoryActivity::class.java))
        }
        binding.actionLogout.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(this@StoryActivity, LoginActivity::class.java))
            finish()
        }

        binding.actionChangeLanguage.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
        binding.actionMaps.setOnClickListener {
            startActivity(Intent(this@StoryActivity, MapsActivity::class.java))
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getStories()
        viewModel.getUser()
    }

    private fun initAdapter() {
        binding.rvStory.adapter = adapter.withLoadStateFooter(footer = LoadingStateAdapter {
            adapter.retry()
        })
        binding.rvStory.layoutManager = LinearLayoutManager(this)
    }
}