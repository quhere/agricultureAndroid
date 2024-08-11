package com.example.testactivity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.testactivity.data.Order
import com.example.testactivity.data.Supplier
import com.example.testactivity.databinding.ActivityProductBinding
import com.example.testactivity.databinding.ActivitySupplierBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SupplierActivity : AppCompatActivity() {
    private lateinit var supplierBinding: ActivitySupplierBinding
    private val api = Retrofit.Builder()
        .baseUrl(SystemVariable.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MyApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supplierBinding = ActivitySupplierBinding.inflate(layoutInflater)
        setContentView(supplierBinding.root)

        // Get data from intent
        val supplierId = intent.getLongExtra("supplierId", 0)
        Log.i("XXXXXXXX suppId", supplierId.toString())

        supplierId?.let { getSupplier(it) }
    }

    private fun getSupplier(supplierId: Long) {
        api.getSupplier(supplierId).enqueue(object : Callback<Supplier> {
            override fun onResponse(call: Call<Supplier>, response: Response<Supplier>) {
                if ( response.isSuccessful ) {
                    response.body()?.let { supplier ->
                        Log.i("SSSSSSSSS", "INFO OF SUPPLIER: ${supplier.supplierId}; " +
                                "${supplier.name}; " +
                                "${supplier.address}; " +
                                "${supplier.email}; " +
                                "${supplier.phoneNumber}; " +
                                "${supplier.fax}; " +
                                "${supplier.avtUrl}; ")
                        supplierBinding.supplierName.text = supplier.name
                        supplier.name?.let { Log.i("SUPP NAME", it) }
                        supplierBinding.supplierAddress.text = supplier.address
                        supplierBinding.supplierEmail.text = supplier.email
                        supplierBinding.supplierPhone.text = supplier.phoneNumber
                        supplierBinding.supplierFax.text = supplier.fax
                        supplierBinding.supplierTaxCode.text = supplier.taxCode
                        supplierBinding.supplierEstablishment.text = supplier.establishment
                        supplierBinding.supplierManager.text = supplier.manager
                        supplierBinding.supplierActivated.text = supplier.activated
                        supplierBinding.supplierDescription.text = supplier.description
                        supplier.avtUrl?.let {
                            Glide.with(supplierBinding.root)
                                .load(it)
                                .into(supplierBinding.imageSupplier)
                            Log.i("ANH NE", it)
                        }
                    }
                }
            }

            override fun onFailure(p0: Call<Supplier>, p1: Throwable) {
                Log.i("FFFFFFF", "INFO OF SUPPLIER:", p1)
            }

        })
    }
}