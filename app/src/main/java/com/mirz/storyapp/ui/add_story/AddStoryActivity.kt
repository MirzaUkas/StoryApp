package com.mirz.storyapp.ui.add_story

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.mirz.storyapp.Locator
import com.mirz.storyapp.R
import com.mirz.storyapp.databinding.ActivityAddStoryBinding
import com.mirz.storyapp.utils.*
import java.io.File

class AddStoryActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddStoryBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<AddStoryViewModel>(factoryProducer = { Locator.addStoryViewModelFactory })
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latLng: LatLng? = null
    private var getFile: File? = null
    private lateinit var currentPhotoPath: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (!REQUIRED_CAMERA_PERMISSIONS.checkPermissionsGranted(baseContext)) {
            ActivityCompat.requestPermissions(
                this, REQUIRED_CAMERA_PERMISSIONS, REQUEST_CODE_CAMERA_PERMISSION
            )
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        binding.btGallery.setOnClickListener { startGallery() }
        binding.switchLocation.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                getMyLastLocation()
            }
        }
        binding.btCamera.setOnClickListener {
            startTakePhoto()
        }
        binding.btAddStory.setOnClickListener {
            if (getFile != null) {
                getFile?.let {
                    viewModel.addStory(
                        it.reduceFileImage(), binding.edAddDescription.text.toString(),
                        latLng
                    )
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
                        this@AddStoryActivity,
                        getString(R.string.add_story_success),
                        Toast.LENGTH_SHORT
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

    @SuppressLint("MissingPermission")
    private fun getMyLastLocation() {
        if (REQUIRED_LOCATION_PERMISSIONS.checkPermissionsGranted(baseContext)) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    latLng = LatLng(location.latitude, location.longitude)
                } else {
                    binding.switchLocation.isEnabled = false
                    Toast.makeText(
                        this@AddStoryActivity,
                        getString(R.string.location_not_found),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
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

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                // Precise location access granted.
                getMyLastLocation()
            }

            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                // Only approximate location access granted.
                getMyLastLocation()
            }

            else -> {
                // No location access granted.
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_CAMERA_PERMISSION) {
            if (!REQUIRED_CAMERA_PERMISSIONS.checkPermissionsGranted(baseContext)) {
                Toast.makeText(
                    this, getString(R.string.cant_get_camera_permission), Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        } else if (requestCode == REQUEST_CODE_LOCATION_PERMISSIONS) {
            if (!REQUIRED_LOCATION_PERMISSIONS.checkPermissionsGranted(baseContext)) {
                Toast.makeText(
                    this, getString(R.string.cant_get_location_permission), Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }


    }

    companion object {
        private val REQUIRED_CAMERA_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA
        )
        private val REQUIRED_LOCATION_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        )

        private const val REQUEST_CODE_CAMERA_PERMISSION = 10
        private const val REQUEST_CODE_LOCATION_PERMISSIONS = 11
    }
}