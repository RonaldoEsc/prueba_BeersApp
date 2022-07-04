package net.hrec.pruebatecnica.usecases.favorites

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.hrec.pruebatecnica.model.BeersResponse
import net.hrec.pruebatecnica.provider.sqlite.SQLiteApp

class FavoritesViewModel(): ViewModel() {
    val favoriteBeersList = MutableLiveData<List<BeersResponse>>()

    fun getFavorites(context: Context) {
        val data = SQLiteApp(context)
        favoriteBeersList.postValue(data.getAllFavorites())
    }
}