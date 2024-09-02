package com.trx.activities

import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.trx.databinding.ActivityZxingBinding

class ZxingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityZxingBinding
    private lateinit var barLauncher: ActivityResultLauncher<ScanOptions>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityZxingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Register the ActivityResultLauncher in onCreate
        barLauncher = registerForActivityResult(ScanContract()) { result ->
            if (result.contents != null) {
                val alertDialog = AlertDialog.Builder(this).also {
                    it.setTitle("Result")
                    it.setMessage(result.contents)
                    it.setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                }.create()
                alertDialog.show()
            }
        }

        // Set up the button click listener
        binding.btnScan.setOnClickListener {
            scan()
        }
    }

    private fun scan() {
        val scanOptions = ScanOptions().also {
            it.setBeepEnabled(true)
            it.setOrientationLocked(true)
            it.setCaptureActivity(ZxingParent::class.java)
        }

        // Launch the scan
        barLauncher.launch(scanOptions)
    }
}
