package com.mirz.storyapp.ui.maps

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.mirz.storyapp.Locator
import com.mirz.storyapp.R
import com.mirz.storyapp.databinding.ActivityMapsBinding
import com.mirz.storyapp.utils.ResultState
import com.mirz.storyapp.utils.launchAndCollectIn


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val binding by lazy { ActivityMapsBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MapsViewModel>(factoryProducer = { Locator.mapsViewModelFactory })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this@MapsActivity)
        viewModel.getStories()

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this@MapsActivity, R.raw.map_style))
        displayMarker()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun displayMarker() {
        viewModel.mapsState.launchAndCollectIn(this) {
            when (it.resultStories) {
                is ResultState.Success -> {
                    binding.progressBar.visibility = android.view.View.GONE
                    it.resultStories.data?.forEach { story ->
                        val position = LatLng(story.lat, story.lng)
                        mMap.addMarker(MarkerOptions().position(position).title(story.name))
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 5f))
                    }
                }

                is ResultState.Error -> {
                    binding.progressBar.visibility = android.view.View.GONE
                    Toast.makeText(this@MapsActivity, it.resultStories.message, Toast.LENGTH_SHORT)
                        .show()
                }

                is ResultState.Loading -> binding.progressBar.visibility = android.view.View.VISIBLE

                else -> Unit
            }
        }
    }


}