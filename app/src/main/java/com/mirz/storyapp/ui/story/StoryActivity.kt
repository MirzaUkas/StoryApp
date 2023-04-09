package com.mirz.storyapp.ui.story

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mirz.storyapp.Locator
import com.mirz.storyapp.databinding.ActivityStoryBinding
import com.mirz.storyapp.ui.add_story.AddStoryActivity
import com.mirz.storyapp.ui.login.LoginActivity
import com.mirz.storyapp.utils.ResultState
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
            when (it.resultStories) {
                is ResultState.Success -> {
                    binding.progressBar.visibility = android.view.View.GONE
                    it.resultStories.data?.let { stories -> adapter.setItems(stories) }
                }
                is ResultState.Error -> {
                    binding.progressBar.visibility = android.view.View.GONE
                    Toast.makeText(this@StoryActivity, it.resultStories.message, Toast.LENGTH_SHORT)
                        .show()
                }
                is ResultState.Loading ->
                    binding.progressBar.visibility = android.view.View.VISIBLE

                else -> Unit
            }

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

    }

    override fun onResume() {
        super.onResume()
        viewModel.getStories()
        viewModel.getUser()
    }

    private fun initAdapter() {
        binding.rvStory.adapter = adapter
        binding.rvStory.layoutManager = LinearLayoutManager(this)
    }
}