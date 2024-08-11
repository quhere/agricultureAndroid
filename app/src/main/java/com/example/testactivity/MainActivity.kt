package com.example.testactivity

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.testactivity.databinding.ActivityMainBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            isGranted : Boolean ->
            if (isGranted) {
                showCamera()
            }
            else {
                //
            }
        }

    private val scanLauncher =
        registerForActivityResult(ScanContract()) { result: ScanIntentResult ->
            run {
                if (result.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
                } else {
                    setResult(result.contents)
                    val linkInfo = result.contents
                    // Giả sử result.contents chứa JSON với các ID
//                    val data = JSONObject(result.contents)
//                    val productId = data.getString("productId")
//                    val transportId = data.getString("transportId")
//                    val sellerId = data.getString("sellerId")
                    val intent = Intent(this, ProductActivity::class.java)
                    val transactionId = linkInfo.substringAfterLast('/').toLong()

                    if ( linkInfo.contains("api/products") ) {
                        intent.putExtra("productId", transactionId)
                        intent.putExtra("hasSeller", "no")
                    }
                    else if ( linkInfo.contains("distributors/supplier") ) {
                        intent.putExtra("transactionId", transactionId)
                        intent.putExtra("hasSeller", "no")
                    }
                    else {
                        intent.putExtra("transactionId", transactionId)
                        intent.putExtra("hasSeller", "yes")
                    }
                    Log.i("XXXXXXXXXXXX", result.contents)

                    // Chuyển sang ProductActivity
//                    val intent = Intent(this, ProductActivity::class.java)
//                    intent.putExtra("productId", productId)
//                    intent.putExtra("transportId", transportId)
//                    intent.putExtra("sellerId", sellerId)
                    startActivity(intent)
                }

            }
        }

    private lateinit var binding: ActivityMainBinding

    private fun setResult(string: String) {
        binding.textResult.text = string
    }

    private fun showCamera() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Scan QR code")
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(true)
        options.setOrientationLocked(false)

        scanLauncher.launch(options)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        initBinding()
        initView()

    }

    private fun initView() {
        binding.fab.setOnClickListener {
            checkPermissionCamera(this)
        }

    }

    private fun checkPermissionCamera(context: Context) {
        if(ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            showCamera()
        }
        else if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)){
            Toast.makeText(context, "CAMERA permission required", Toast.LENGTH_SHORT).show()
        }
        else {
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    private fun initBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}