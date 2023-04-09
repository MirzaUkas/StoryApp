package com.mirz.storyapp.ui.welcome

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.mirz.storyapp.Locator
import com.mirz.storyapp.databinding.ActivityWelcomeBinding
import com.mirz.storyapp.ui.login.LoginActivity
import com.mirz.storyapp.ui.story.StoryActivity
import com.mirz.storyapp.utils.ResultState
import com.mirz.storyapp.utils.launchAndCollectIn

class WelcomeActivity : AppCompatActivity() {
    private val binding by lazy { ActivityWelcomeBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<WelcomeViewModel>(factoryProducer = { Locator.welcomeViewModelFactory })
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        hideSystemUI()
        viewModel.welcomeState.launchAndCollectIn(this) {
            if (it.resultIsLoggedIn is ResultState.Success) {
                if (it.resultIsLoggedIn.data == true) {
                    startActivity(
                        Intent(this@WelcomeActivity, StoryActivity::class.java).addFlags(
                            Intent.FLAG_ACTIVITY_CLEAR_TOP
                        )
                    )
                    finish()
                } else {
                    startActivity(
                        Intent(this@WelcomeActivity, LoginActivity::class.java).addFlags(
                            Intent.FLAG_ACTIVITY_CLEAR_TOP
                        )
                    )
                    finish()
                }
            }
        }

    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

}