package com.copilot

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.copilot.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(
            applicationContext,
            android.R.string.ok, Toast.LENGTH_SHORT
        ).show()
    }
    val negativeButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(
            applicationContext,
            android.R.string.cancel, Toast.LENGTH_SHORT
        ).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        navView.findViewById<View>(R.id.navigation_dashboard).setOnLongClickListener {
            Log.i("MainActivity", "onLongClick: Dashboard")
            connectedVehiclesAlert(it)
            true
        }

        val dashboardTab = navView.findViewById<BottomNavigationItemView>(R.id.navigation_dashboard)
        val badge: View =
            LayoutInflater.from(this).inflate(R.layout.badge_plus, dashboardTab, false)
        dashboardTab.addView(badge)
    }

    fun connectedVehiclesAlert(view: View) {
        val items = arrayOf("ESP32 [1]", "ESP32 [2]", "ESP32 [3]")
        val builder = AlertDialog.Builder(this)

        with(builder)
        {
            setTitle("Your Copilot devices")
            setItems(items) { dialog, which ->
                Toast.makeText(applicationContext, items[which] + " is chosen", Toast.LENGTH_SHORT)
                    .show()
                // TODO: Pair and request data
            }
            setPositiveButton(
                "Add",
                DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                    addVehicleAlert(view)
                })
            setNegativeButton(android.R.string.cancel, negativeButtonClick)
            show()
        }
    }

    fun addVehicleAlert(view: View) {
        val items = arrayOf(
            "ESP32 [::12]",
            "ESP32 [98:11:11:11:12:23]",
            "ESP32 [9876::12234]",
            "ESP32 [9876::12234]",
            "ESP32 [9876::12234]",
            "ESP32 [9876::12234]",
            "ESP32 [9876::12234]",
            "ESP32 [9876::12234]",
            "ESP32 [9876::12234]",
            "ESP32 [9876::12234]",
            "ESP32 [9876::12234]",
            "ESP32 [1]",
            "ESP32 [1]",
            "ESP32 [2]"
        )
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("Choose active device")
            setItems(items) { dialog, which ->
                Toast.makeText(applicationContext, items[which] + " is clicked", Toast.LENGTH_SHORT)
                    .show()
            }
            setPositiveButton(
                "Add",
                DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                    // TODO: Pair and request data
                })
            setNegativeButton(android.R.string.cancel, negativeButtonClick)
        }

        val alertDialog = builder.create()
        alertDialog.show()
        val params = WindowManager.LayoutParams()
        params.copyFrom(alertDialog.window?.attributes)
        val screenHeightPercentage = 0.60
        params.height = (resources.displayMetrics.heightPixels * screenHeightPercentage).toInt()
        alertDialog.window?.attributes = params
    }
}
