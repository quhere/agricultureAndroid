package com.example.testactivity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.testactivity.data.Distributor
import com.example.testactivity.data.Product
import com.example.testactivity.databinding.ActivityTransportBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TransportActivity : AppCompatActivity() {
    private lateinit var transportBinding: ActivityTransportBinding
    private val api = Retrofit.Builder()
        .baseUrl(SystemVariable.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MyApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transportBinding = ActivityTransportBinding.inflate(layoutInflater)
        setContentView(transportBinding.root)

        // Get data from intent
        val distributorId = intent.getLongExtra("distributorId", 0)
        Log.i("ddddddddddd", distributorId.toString())
        getDistributor(distributorId)

        // Example data
        transportBinding.tvTransportTitle.text = "Đại lý/Nhà phân phối"


    }

    private fun getDistributor(distributorId: Long) {


        api.getDistributor(distributorId).enqueue(object : Callback<Distributor> {
            override fun onResponse(call: Call<Distributor>, response: Response<Distributor>) {
                if (response.isSuccessful) {
                    response.body()?.let {distributor ->
                        Log.i("DDDDDDDD", "INFO OF DISTRIBUTOR: ${distributor.distributorId} ;" +
                                " ${distributor.name}")
                        transportBinding.distributorName.text = distributor.name
                        transportBinding.distributorEmail.text = distributor.email
                        transportBinding.distributorAddress.text = distributor.address
                        transportBinding.distributorPhone.text = distributor.phoneNumber
                        transportBinding.distributorFax.text = distributor.fax
                        transportBinding.distributorTaxCode.text = distributor.taxCode
                        transportBinding.distributorManager.text = distributor.manager
                        transportBinding.distributorActivated.text = distributor.activated
                        transportBinding.distributorEstablishment.text = distributor.establishment
                        transportBinding.distributorDescription.text = distributor.description
                        distributor.avtUrl?.let {
                            Glide.with(transportBinding.root)
                                .load(it)
                                .into(transportBinding.imageDistributor)
                            Log.i("ANH NE", it)
                        }
                    }
                }
                else {
                    Log.e("Response Error", response.errorBody()?.string() ?: "Unknown error")
                }
            }

            override fun onFailure(p0: Call<Distributor>, p1: Throwable) {
                Log.i("BBBBBBBBBB", "Fail", p1);
            }

        })
    }
}