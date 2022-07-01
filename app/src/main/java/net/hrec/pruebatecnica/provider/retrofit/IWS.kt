package net.hrec.pruebatecnica.provider.retrofit

import net.hrec.pruebatecnica.model.BeersResponse
import net.hrec.pruebatecnica.model.DetailBeerResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface IWS {

    @Headers("Content-Type:application/json")
    @GET("beers")
    fun allMenuHome(@Query("page") page:Int, @Query("per_page") perPage:Int): Call<List<BeersResponse>>

    @Headers("Content-Type:application/json")
    @GET("beers/{id}")
    fun getBeerDescription(@Path("id") id: Int): Call<List<DetailBeerResponse>>

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