package com.mirz.storyapp.ui.add_story

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.mirz.storyapp.Locator
import com.mirz.storyapp.R
import com.mirz.storyapp.databinding.ActivityAddStoryBinding
import com.mirz.storyapp.utils.*
import java.io.File

class AddStoryActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddStoryBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<AddStoryViewModel>(factoryProducer = { Locator.addStoryViewModelFactory })
    private var getFile: File? = null
    private lateinit var currentPhotoPath: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.btGallery.setOnClickListener { startGallery() }
        binding.btCamera.setOnClickListener {
            startTakePhoto()
        }
        binding.btAddStory.setOnClickListener {
            if (getFile != null) {
                getFile?.let {
                    viewModel.addStory(it.reduceFileImage(), binding.edAddDescription.text.toString())
                }
            } else {
                Toast.makeText(this, getString(R.string.please_choose_image), Toast.LENGTH_SHORT)
                    .show()
            }
        }

        viewModel.addStoryState.launchAndCollectIn(this@AddStoryActivity) { state ->
            when (state.resultAddStory) {
                is ResultState.Success<String> -> {
                    binding.btAddStory.setLoading(false)
                    Toast.makeText(
                        this@AddStoryActivity, getString(R.string.add_story_success), Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
                is ResultState.Loading -> binding.btAddStory.setLoading(true)
                is ResultState.Error -> {
                    binding.btAddStory.setLoading(false)
                    Toast.makeText(
                        this@AddStoryActivity, state.resultAddStory.message, Toast.LENGTH_SHORT
                    ).show()
                }
                else -> Unit
            }
        }
    }


    @SuppressLint("QueryPermissionsNeeded")
    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@AddStoryActivity, packageName, it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }


    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile
            val result = BitmapFactory.decodeFile(myFile.path)
            binding.ivPreviewPhoto.setImageBitmap(result)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = selectedImg.uriToFile(this@AddStoryActivity)
            getFile = myFile
            binding.ivPreviewPhoto.setImageURI(selectedImg)
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    getString(R.string.cant_get_permission),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}