package net.hrec.pruebatecnica.usecases.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import net.hrec.pruebatecnica.model.BeersResponse
import net.hrec.pruebatecnica.provider.retrofit.IWS
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {
    private val service = IWS.create()
    val beersList = MutableLiveData<List<BeersResponse>>()

    private val _closeSession = MutableLiveData<Boolean>()
    val closeSession: LiveData<Boolean> = _closeSession

    fun getBeers(page: Int, perPage: Int) {
        val call = service.allMenuHome(page, perPage)
        call.enqueue(object : Callback<List<BeersResponse>> {
            override fun onResponse(call: Call<List<BeersResponse>>, response: Response<List<BeersResponse>>) {
                response.body().let {
                    beersList.postValue(it)
                }
            }

            override fun onFailure(call: Call<List<BeersResponse>>, t: Throwable) {
                call.cancel()
            }

        })
    }

    fun closeSession() {
        FirebaseAuth.getInstance().signOut().let {
            _closeSession.postValue(true)
        }
    }
}