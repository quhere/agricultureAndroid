package com.example.testactivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.testactivity.data.Info
import com.example.testactivity.data.Order
import com.example.testactivity.data.Product
import com.example.testactivity.databinding.ActivityProductBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductActivity : AppCompatActivity() {

    private lateinit var productBinding: ActivityProductBinding
    private var transactionId: Long? = 0
    private var productId: Long? = 0
    private var sellerId: Long? = 0
    private var distributorId: Long? = 0
    private var supplierId: Long? = 0
    private val api = Retrofit.Builder()
        .baseUrl(SystemVariable.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MyApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productBinding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(productBinding.root)

        // Get data from intent
        productId = intent.getLongExtra("productId", 0)
        val transactionId = intent.getLongExtra("transactionId", 0)
        val hasSeller = intent.getStringExtra("hasSeller")
        Log.i("NOT RUN YET: productId", productId.toString())
        Log.i("NOT RUN YET: hasSeller", transactionId.toString())
        Log.i("NOT RUN YET: hasSeller", hasSeller.toString())

//        transactionId?.let { getProduct(it) }


        if (hasSeller != null && hasSeller == "yes") {
            Log.i("GET INFOOOOOOOO", "GET INFO")
            getInfo(transactionId)
            productBinding.btnViewSupplier.setOnClickListener {
                val intent = Intent(this, SupplierActivity::class.java)
                intent.putExtra("supplierId", supplierId)
                startActivity(intent)
            }

            productBinding.btnViewTransport.setOnClickListener {
                val intent = Intent(this, TransportActivity::class.java)
                intent.putExtra("distributorId", distributorId)
                startActivity(intent)
            }

            productBinding.btnViewSeller.setOnClickListener {
                val intent = Intent(this, SellerActivity::class.java)
                intent.putExtra("sellerId", sellerId)
                startActivity(intent)
            }
        } else if (hasSeller != null && hasSeller == "no" && productId!!.toInt() == 0) {
            getTransactionWithSupplier(transactionId)
            productBinding.btnViewSupplier.setOnClickListener {
                val intent = Intent(this, SupplierActivity::class.java)
                intent.putExtra("supplierId", supplierId)
                startActivity(intent)
            }

            productBinding.btnViewTransport.setOnClickListener {
                val intent = Intent(this, TransportActivity::class.java)
                intent.putExtra("distributorId", distributorId)
                startActivity(intent)
            }

            productBinding.btnViewSeller.visibility = Button.GONE
        } else {
            getProduct(productId!!)
            productBinding.btnViewSupplier.setOnClickListener {
                val intent = Intent(this, SupplierActivity::class.java)
                intent.putExtra("supplierId", supplierId)
                startActivity(intent)
            }

            productBinding.btnViewTransport.visibility = Button.GONE
            productBinding.btnViewSeller.visibility = Button.GONE
        }
    }

    private fun getInfo(warehouseId: Long) {
        api.getInfo(warehouseId).enqueue(object : Callback<Info> {
            override fun onResponse(call: Call<Info>, response: Response<Info>) {
                if (response.isSuccessful) {
                    response.body()?.let { info ->
                        Log.i("IIIII", "INFO OF PRODUCT: ${info.product?.productName}")
                        productBinding.tvProductName.text = info.product?.productName
                        productBinding.tvPlantingDate.text = info.product?.plantingDate
                        productBinding.tvHarvestDate.text = info.product?.harvestDate
                        productBinding.tvCharacteristic.text = info.product?.characteristic
                        productBinding.tvSeed.text = info.product?.seed
                        productBinding.tvCook.text = info.product?.cook
                        productBinding.tvNote.text = info.product?.note
                        productBinding.tvProductBrand.text = info.product?.productBrand
                        productBinding.tvProductCertification.text = info.product?.productCertification
                        productBinding.tvProductOrigin.text = info.product?.productOrigin
                        productBinding.tvProductCommit.text = info.product?.productCommit
                        productBinding.tvProductWeight.text = info.product?.productWeight
                        productBinding.tvProductPlanting.text = info.product?.productPlanting
                        info.product?.image?.let {
                            Glide.with(productBinding.root)
                                .load(it)
                                .into(productBinding.productImg)
                            Log.i("ANH NE", it)
                        }
                        productId = info.product?.supplierId
                        supplierId = info.supplier?.supplierId
                        distributorId = info.distributor?.distributorId
                        sellerId = info.seller?.sellerId
                        Log.i("RUN YET: productId", productId.toString())
                        Log.i("RUN YET: supplierId", supplierId.toString())
                        Log.i("RUN YET: supplierId", supplierId.toString())
                        Log.i("RUN YET: sellerId", sellerId.toString())

                    }
                }
            }

            override fun onFailure(p0: Call<Info>, p1: Throwable) {
                Log.i("FIIIII", "INFO OF PRODUCT:", p1)
            }
        })
    }

    private fun getTransactionWithSupplier(transactionId: Long) {
        api.getOrder(transactionId).enqueue(object : Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    response.body()?.let { order ->
                        Log.i("AAAAAAAAAA", "INFO OF PRODUCT: ${order.id}")
                        productBinding.tvProductName.text = order.product.productName
                        productBinding.tvPlantingDate.text = order.product.plantingDate
                        productBinding.tvHarvestDate.text = order.product.harvestDate
                        productBinding.tvCharacteristic.text = order.product.characteristic
                        productBinding.tvSeed.text = order.product.seed
                        productBinding.tvCook.text = order.product.cook
                        productBinding.tvNote.text = order.product.note
                        productBinding.tvProductBrand.text = order.product.productBrand
                        productBinding.tvProductCertification.text = order.product.productCertification
                        productBinding.tvProductOrigin.text = order.product.productOrigin
                        productBinding.tvProductCommit.text = order.product.productCommit
                        productBinding.tvProductWeight.text = order.product.productWeight
                        productBinding.tvProductPlanting.text = order.product.productPlanting
                        order.product.image?.let {
                            Glide.with(productBinding.root)
                                .load(it)
                                .into(productBinding.productImg)
                            Log.i("ANH NE", it)
                        }
                        supplierId = order.product.supplierId
                        distributorId = order.distributorId
                    }
                }
            }
            override fun onFailure(p0: Call<Order>, p1: Throwable) {
                Log.i("BBBBBBBBB", "INFO OF PRODUCT:", p1)
            }
        })
    }

    private fun getProduct(transactionId: Long) {
        Log.i("INTO GET PROD: productId", transactionId.toString())
        api.getProduct(transactionId).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    response.body()?.let { product ->
                        Log.i(
                            "PPPP", "INFO OF PRODUCT: ${product.productId} ;" +
                                    " ${product.productName}"
                        )
                        Log.i("IIII", "INFO OF PRODUCT: ${product.productName}")
                        productBinding.tvProductName.text = product.productName
                        productBinding.tvPlantingDate.text = product.plantingDate
                        productBinding.tvHarvestDate.text = product.harvestDate
                        productBinding.tvCharacteristic.text = product.characteristic
                        productBinding.tvSeed.text = product.seed
                        productBinding.tvCook.text = product.cook
                        productBinding.tvNote.text = product.note
                        productBinding.tvProductBrand.text = product.productBrand
                        productBinding.tvProductCertification.text = product.productCertification
                        productBinding.tvProductOrigin.text = product.productOrigin
                        productBinding.tvProductCommit.text = product.productCommit
                        productBinding.tvProductWeight.text = product.productWeight
                        productBinding.tvProductPlanting.text = product.productPlanting
                        product.image?.let {
                            Glide.with(productBinding.root)
                                .load(it)
                                .into(productBinding.productImg)
                            Log.i("ANH NE", it)
                        }
                        supplierId = product.supplierId
                    }
                } else {
                    Log.e("Response Error", response.errorBody()?.string() ?: "Unknown error")
                }
            }
            override fun onFailure(p0: Call<Product>, p1: Throwable) {
                Log.i("FPPPP", "Fail", p1);
            }

        })
    }
}