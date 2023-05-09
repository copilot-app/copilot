package com.copilot

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.copilot.databinding.ActivityMainBinding
import com.copilot.ui.menu.VehicleMenu
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.MapsInitializer.Renderer
import com.google.android.gms.maps.OnMapsSdkInitializedCallback
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), OnMapsSdkInitializedCallback {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapsInitializer.initialize(applicationContext, Renderer.LATEST, this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        navView.findViewById<View>(R.id.navigation_dashboard).setOnLongClickListener {
            VehicleMenu(this, it)
            true
        }

        val dashboardTab = navView.findViewById<BottomNavigationItemView>(R.id.navigation_dashboard)
        val badge: View =
            LayoutInflater.from(this).inflate(R.layout.badge_plus, dashboardTab, false)
        dashboardTab.addView(badge)
    }

    override fun onMapsSdkInitialized(renderer: Renderer) {
        when (renderer) {
            Renderer.LATEST -> Log.d("MapsSDK", "The latest version of the renderer is used.")
            Renderer.LEGACY -> Log.d("MapsSDK", "The legacy version of the renderer is used.")
        }
    }
}
