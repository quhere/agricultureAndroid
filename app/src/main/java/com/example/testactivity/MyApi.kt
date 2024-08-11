package com.example.testactivity

import com.example.testactivity.data.Distributor
import com.example.testactivity.data.Dog
import com.example.testactivity.data.Info
import com.example.testactivity.data.Order
import com.example.testactivity.data.Product
import com.example.testactivity.data.Seller
import com.example.testactivity.data.Supplier
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MyApi {
    @GET("random")
    fun getDog(): Call<Dog>

    @GET("transactions/{id}")
    fun getInfo(@Path("id") id: Long): Call<Info>

    @GET("products/{id}")
    fun getProduct(@Path("id") id: Long): Call<Product>

    @GET("distributors/{id}")
    fun getDistributor(@Path("id") id: Long): Call<Distributor>

    @GET("sellers/{id}")
    fun getSeller(@Path("id") id: Long): Call<Seller>

    @GET("distributors/supplier/{id}")
    fun getOrder(@Path("id") id: Long): Call<Order>

    @GET("suppliers/{id}")
    fun getSupplier(@Path("id") id: Long): Call<Supplier>
}