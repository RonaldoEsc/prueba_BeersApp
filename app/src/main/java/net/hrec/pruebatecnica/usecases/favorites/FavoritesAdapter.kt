package net.hrec.pruebatecnica.usecases.favorites

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import net.hrec.pruebatecnica.R
import net.hrec.pruebatecnica.databinding.FavoritesViewHolderBinding
import net.hrec.pruebatecnica.model.BeersResponse
import net.hrec.pruebatecnica.provider.sqlite.SQLiteApp

class FavoritesAdapter: RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {
    private var listBeers = mutableListOf<BeersResponse>()
    private var dataBase: SQLiteApp? = null
    inner class FavoriteViewHolder(binding: FavoritesViewHolderBinding):RecyclerView.ViewHolder(binding.root) {
        private val imgBeer = binding.imgBeer
        private val tvBeerName = binding.tvBeerName
        private val tvBeerTagName = binding.tvBeerTagName
        val rateBeer = binding.rateBeer

        fun bind(beer: BeersResponse) {
            tvBeerName.text = beer.name
            tvBeerTagName.text = beer.tagline
            rateBeer.rating = beer.rate.toFloat()
            Picasso.get().load(beer.imageUrl).placeholder(R.drawable.progress_animation).into(imgBeer)
        }
    }

    fun setData(list: List<BeersResponse>) {
        listBeers.clear()
        listBeers.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FavoritesViewHolderBinding.inflate(inflater, parent, false)
        dataBase = SQLiteApp(parent.context)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val beer = listBeers[position]
        holder.bind(beer)
        holder.rateBeer.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            if (fromUser) {
                beer.rate = rating.toInt()
                dataBase?.updateRate(beer.id!!, beer.rate)
                Log.v( "FavoritesAdapter", "${ratingBar.rating} comparar con: $rating")
            }
        }
    }

    override fun getItemCount(): Int {
        return listBeers.size
    }
}