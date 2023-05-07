package com.copilot.ui.menu

import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.copilot.data.DummyData

class VehicleMenu(private val context: Context, view: View) {

    private val screenHeightPercentage = 0.60

    init {
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

    private fun showNewDeviceAlert(view: View) {
        val builder = AlertDialog.Builder(context)

        with(builder)
        {
            setTitle("Choose active device")
            setItems(DummyData.pairedBluetoothDevices) { _, which ->
                Toast.makeText(
                    context,
                    DummyData.pairedBluetoothDevices[which] + " clicked",
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