package net.hrec.pruebatecnica.usecases.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import net.hrec.pruebatecnica.model.ListBeersResponse
import net.hrec.pruebatecnica.provider.retrofit.IWS
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {
    private val service = IWS.create()
    private val beersList = MutableLiveData<ListBeersResponse>()

    fun getBeers(page: Int, perPage: Int) {
        val call = service.allMenuHome(page, perPage)
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                response.let {

                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                call.cancel()
            }

        })
    }
}