package com.copilot.ui.menu

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothManager
import android.content.*
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat.*
import com.copilot.data.DummyData

const val TAG = "BluetoothLeService"

class VehicleMenu(private val context: Context, view: View) {

    private val screenHeightPercentage = 0.60

    private var bluetoothManager: BluetoothManager? = null
    private val bluetoothGatt: BluetoothGatt? = null
    private val bluetoothAdapter: BluetoothAdapter?

    init {
        bluetoothManager =
            getSystemService(context, BluetoothManager::class.java)
        bluetoothAdapter = bluetoothManager?.adapter
        if (bluetoothAdapter == null)
            Log.d("Bluetooth", "Bluetooth is not supported on this device")
        else if (!bluetoothAdapter.isEnabled)
            startActivity(context, Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), null)
        showConnectedDevicesAlert(view)
    }

    private val onNegativeButtonClick = { _: DialogInterface, _: Int ->
        Toast.makeText(context, android.R.string.cancel, Toast.LENGTH_SHORT).show()
    }

    private fun showConnectedDevicesAlert(view: View) {
        val builder = AlertDialog.Builder(context)

        with(builder)
        {
            setTitle("Your Copilot devices")
            setItems(DummyData.connectedDevices) { _, which ->
                Toast.makeText(
                    context,
                    DummyData.connectedDevices[which] + " chosen",
                    Toast.LENGTH_SHORT
                ).show()
                // TODO: Pair and request data
            }
            setPositiveButton("Add") { _: DialogInterface, _: Int ->
                showNewDeviceAlert(view)
            }
            setNegativeButton(android.R.string.cancel) { _: DialogInterface, _: Int ->
                Toast.makeText(context, android.R.string.cancel, Toast.LENGTH_SHORT).show()
            }
        }

        setAlertHeight(builder.create())
    }

    @SuppressLint("MissingPermission")
    private fun showNewDeviceAlert(view: View) {
        var deviceNames = arrayOf("")
        if (checkSelfPermission(
                context,
                android.Manifest.permission.BLUETOOTH
            ) == PERMISSION_GRANTED
        ) {
            val pairedDevices = bluetoothAdapter?.bondedDevices
            deviceNames = pairedDevices?.map { device: BluetoothDevice -> device.name }
                ?.toTypedArray() ?: arrayOf()
        } else {
            requestPermissions(
                context as Activity,
                arrayOf(android.Manifest.permission.BLUETOOTH),
                1
            )
        }

        val builder = AlertDialog.Builder(context)

        with(builder)
        {
            setTitle("Choose active device")
            setItems(deviceNames) { _, which ->
                Toast.makeText(
                    context,
                    deviceNames[which] + " clicked",
                    Toast.LENGTH_SHORT
                ).show()
            }
            setPositiveButton("Add", DialogInterface.OnClickListener { _: DialogInterface, _: Int ->
                // TODO: Pair and request data
            })
            setNegativeButton(android.R.string.cancel, onNegativeButtonClick)
        }

        setAlertHeight(builder.create())
    }

    private fun setAlertHeight(alertDialog: AlertDialog) {
        alertDialog.show()
        alertDialog.window?.decorView?.post {
            val currentHeight = alertDialog.window?.decorView?.measuredHeight
            val maxHeight =
                (context.resources.displayMetrics.heightPixels * screenHeightPercentage).toInt()
            alertDialog.window?.setLayout(
                alertDialog.window?.decorView?.measuredWidth!!,
                (currentHeight!!).coerceAtMost(maxHeight)
            )
        }
    }
}