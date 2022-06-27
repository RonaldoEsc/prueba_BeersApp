package net.hrec.pruebatecnica.provider.retrofit

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface IWS {

    @Headers("Content-Type:application/json")
    @GET("beers")
    fun allMenuHome(@Query("page") page:Int, @Query("per_page") perPage:Int): Call<JsonObject>

    @Headers("Content-Type:application/json")
    @GET("beers/{id}")
    fun getBeerDescription(@Path("id") id: String): Call<JsonObject>

    companion object {
        fun create(): IWS {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.punkapi.com/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(IWS::class.java)
        }
    }
}