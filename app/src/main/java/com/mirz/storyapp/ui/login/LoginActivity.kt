package com.mirz.storyapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mirz.storyapp.Locator
import com.mirz.storyapp.databinding.ActivityLoginBinding
import com.mirz.storyapp.ui.register.RegisterActivity
import com.mirz.storyapp.ui.story.StoryActivity
import com.mirz.storyapp.utils.ResultState
import com.mirz.storyapp.utils.launchAndCollectIn

class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<LoginViewModel>(factoryProducer = { Locator.loginViewModelFactory })
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.loginState.launchAndCollectIn(this) { state ->
            when (state.resultVerifyUser) {
                is ResultState.Success<String> -> {
                    binding.btLogin.setLoading(false)
                    startActivity(
                        Intent(
                            this@LoginActivity, StoryActivity::class.java
                        )
                    )
                    finish()
                }
                is ResultState.Loading -> binding.btLogin.setLoading(true)
                is ResultState.Error -> {
                    binding.btLogin.setLoading(false)
                    Toast.makeText(
                        this@LoginActivity, state.resultVerifyUser.message, Toast.LENGTH_SHORT
                    ).show()
                }
                else -> Unit
            }

        }

        binding.btLogin.setOnClickListener {
            viewModel.doLogin(
                email = binding.edLoginEmail.text.toString(),
                password = binding.edLoginPassword.text.toString()
            )
        }

        binding.tvDonTHaveAnAccount.setOnClickListener {
            startActivity(
                Intent(
                    this, RegisterActivity::class.java
                )
            )
        }
    }
}