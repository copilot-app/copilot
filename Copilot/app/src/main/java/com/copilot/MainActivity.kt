package com.copilot

import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.copilot.databinding.ActivityMainBinding
import com.copilot.ui.dashboard.DashboardFragment
import com.copilot.ui.menu.BluetoothLeService
import com.copilot.ui.menu.TAG
import com.copilot.ui.menu.VehicleMenu
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.MapsInitializer.Renderer
import com.google.android.gms.maps.OnMapsSdkInitializedCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.concurrent.timer
import kotlin.random.Random

class MainActivity : AppCompatActivity(), OnMapsSdkInitializedCallback {

    private lateinit var binding: ActivityMainBinding
    private var bluetoothService: BluetoothLeService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gattServiceIntent = Intent(this, BluetoothLeService::class.java)
        bindService(gattServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE)

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

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(
            componentName: ComponentName,
            service: IBinder
        ) {
            bluetoothService = (service as BluetoothLeService.LocalBinder).getService()
            bluetoothService?.let { bluetooth ->
                if (!bluetooth.initialize()) {
                    Log.e(TAG, "Unable to initialize Bluetooth")
                    finish()
                }
                val deviceAddress = resources.getString(R.string.device_address)
                Log.d(TAG, deviceAddress)
                bluetooth.connect(deviceAddress)
            }
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            bluetoothService = null
        }
    }

    private val gattUpdateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                BluetoothLeService.ACTION_GATT_CONNECTED -> {
                }
                BluetoothLeService.ACTION_GATT_DISCONNECTED -> {
                }
                BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED -> {
                    timer(period = 2000) {
                        bluetoothService?.readLocation()
                    }
                }
                BluetoothLeService.ACTION_DATA_AVAILABLE -> {
                    val locationString = intent.getStringExtra(BluetoothLeService.EXTRA_DATA)
                    location = LatLng(
                        locationString!!.split(",")[0].toDouble(),
                        locationString.split(",")[1].toDouble()
                    )
                    val navHostFragment =
                        supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)
                    val currentFragment =
                        navHostFragment?.childFragmentManager?.fragments?.get(0)
                    if (currentFragment is DashboardFragment) {
                        currentFragment.updateLocation()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(gattUpdateReceiver, makeGattUpdateIntentFilter())
        if (bluetoothService != null) {
            val result = bluetoothService!!.connect(R.string.device_address.toString())
            Log.d(TAG, "Connect request result=$result")
        }
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(gattUpdateReceiver)
    }

    private fun makeGattUpdateIntentFilter(): IntentFilter {
        return IntentFilter().apply {
            addAction(BluetoothLeService.ACTION_GATT_CONNECTED)
            addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED)
            addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED)
            addAction(BluetoothLeService.ACTION_DATA_AVAILABLE)
        }
    }

    companion object {
        var location: LatLng? = null
    }
}
