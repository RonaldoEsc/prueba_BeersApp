package net.hrec.pruebatecnica.usecases.detalle

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.hrec.pruebatecnica.model.BeersResponse
import net.hrec.pruebatecnica.provider.retrofit.IWS
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetalleBeerViewModel: ViewModel() {
    private val service = IWS.create()
    val beersList = MutableLiveData<BeersResponse>()

    fun getBeers(id: Int) {
        if (id != -1) {
            val call = service.getBeerDescription(id)
            call.enqueue(object : Callback<BeersResponse> {
                override fun onResponse(
                    call: Call<BeersResponse>,
                    response: Response<BeersResponse>
                ) {
                    response.body().let {
                        beersList.postValue(it)
                    }
                }

                override fun onFailure(call: Call<BeersResponse>, t: Throwable) {
                    call.cancel()
                }

            })
        } else {
            Log.e("getBeers", " error al momento de obtener de arguments el id")
        }
    }
}