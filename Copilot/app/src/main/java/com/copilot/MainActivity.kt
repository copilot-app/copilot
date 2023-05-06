package com.copilot

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
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
        Toast.makeText(applicationContext,
            android.R.string.yes, Toast.LENGTH_SHORT).show()
    }
    val negativeButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(applicationContext,
            android.R.string.no, Toast.LENGTH_SHORT).show()
    }
    val neutralButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(applicationContext,
            "Maybe", Toast.LENGTH_SHORT).show()
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

    fun connectedVehiclesAlert(view: View){
        val builder = AlertDialog.Builder(this)

        with(builder)
        {
            setTitle("Your vehicles")

            setPositiveButton("Add", DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                addVehicleAlert(view)
            })
            setNegativeButton(android.R.string.cancel, negativeButtonClick)
            setNeutralButton("Maybe", neutralButtonClick)
            show()
        }


    }

    fun addVehicleAlert(view: View){
        val items = arrayOf("ESP32 [::12]", "ESP32 [98:11:11:11:12:23]", "ESP32 [9876::12234]", "ESP32 [9876::12234]",
        "ESP32 [9876::12234]", "ESP32 [9876::12234]", "ESP32 [9876::12234]", "ESP32 [9876::12234]", "ESP32 [9876::12234]",
        "ESP32 [9876::12234]", "ESP32 [9876::12234]", "ESP32 [1]", "ESP32 [1]", "ESP32 [2]")
        val builder = AlertDialog.Builder(this)

        with(builder)
        {
            setTitle("New vehicle")
            setItems(items) { dialog, which ->
                Toast.makeText(applicationContext, items[which] + " is clicked", Toast.LENGTH_SHORT).show()
            }
            setPositiveButton("Add", DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->

            })
            setNegativeButton(android.R.string.cancel, negativeButtonClick)
            show()
        }


    }
}
