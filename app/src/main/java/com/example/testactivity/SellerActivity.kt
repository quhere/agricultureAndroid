package com.example.testactivity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.testactivity.data.Seller
import com.example.testactivity.databinding.ActivitySellerBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SellerActivity : AppCompatActivity() {
    private lateinit var sellerBinding: ActivitySellerBinding
    private val api = Retrofit.Builder()
        .baseUrl(SystemVariable.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MyApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sellerBinding = ActivitySellerBinding.inflate(layoutInflater)
        setContentView(sellerBinding.root)

        // Get data from intent
        val sellerId = intent.getLongExtra("sellerId", 0)

        // Example data
        sellerBinding.tvSellerTitle.text = "Nhà bán hàng"

        getSeller(sellerId)

    }

    private fun getSeller(sellerId: Long) {
        api.getSeller(sellerId).enqueue(object : Callback<Seller> {
            override fun onResponse(call: Call<Seller>, response: Response<Seller>) {
                if (response.isSuccessful) {
                    response.body()?.let {seller ->
                        Log.i("SSSS", "INFO OF PRODUCT: ${seller.sellerId} ;" +
                                " ${seller.name}")
                        sellerBinding.sellerName.text = seller.name
                        sellerBinding.sellerAddress.text = seller.address
                        sellerBinding.sellerEmail.text = seller.email
                        sellerBinding.sellerPhone.text = seller.phoneNumber
                        sellerBinding.sellerFax.text = seller.fax
                        sellerBinding.sellerTaxCode.text = seller.taxCode
                        sellerBinding.sellerEstablishment.text = seller.establishment
                        sellerBinding.sellerActivated.text = seller.activated
                        sellerBinding.sellerManager.text = seller.manager
                        sellerBinding.sellerDescription.text = seller.description
                        seller.avtUrl?.let {
                            Glide.with(sellerBinding.root)
                                .load(it)
                                .into(sellerBinding.sellerImg)
                            Log.i("ANH NE", it)
                        }
                    }
                }
                else {
                    Log.e("Response Error", response.errorBody()?.string() ?: "Unknown error")
                }
            }

            override fun onFailure(p0: Call<Seller>, p1: Throwable) {
                Log.i("FSSSS", "Fail", p1);
            }

        })
    }
}