package net.hrec.pruebatecnica.usecases.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import net.hrec.pruebatecnica.R
import net.hrec.pruebatecnica.databinding.BeerViewHolderBinding
import net.hrec.pruebatecnica.model.BeersResponse
import net.hrec.pruebatecnica.provider.sqlite.SQLiteApp
import net.hrec.pruebatecnica.usecases.common.interfaces.DialogEvent

class BeersListAdapter(private val mContext: Fragment, val beerId: (Int) -> Unit): RecyclerView.Adapter<BeersListAdapter.BeerViewHolder>() {
    private var listBeers = mutableListOf<BeersResponse>()
    private val favoriteSelectedList = mutableListOf<Int>()
    private var dataBase: SQLiteApp? = null
    private lateinit var dialog: DialogEvent

    class BeerViewHolder(binding: BeerViewHolderBinding): RecyclerView.ViewHolder(binding.root) {
        private val tvBeerName = binding.tvBeerName
        private val tvBeerTagName = binding.tvBeerTagName
        private val imgBeer = binding.imgBeer
        val ibFavorite = binding.ibFavorite
        val clHolderEvent = binding.clHolderEvent

        fun bind(beer: BeersResponse) {
            tvBeerName.text = beer.name
            tvBeerTagName.text = beer.tagline
            Picasso.get().load(beer.imageUrl).placeholder(R.drawable.progress_animation).into(imgBeer)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<BeersResponse>, context: Context) {
        listBeers.clear()
        favoriteSelectedList.clear()
        listBeers.addAll(list)
        dataBase = SQLiteApp(context)
        var pos = 0
        listBeers.forEach {
            if (dataBase?.isFavoriteBeer(it.id ?: 0) == true) {
                addPosToFavIcon(pos)
            }
            pos += 1
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = BeerViewHolderBinding.inflate(layoutInflater, parent, false)
        dialog = mContext as HomeFragment
        return BeerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        val beer = listBeers[position]
        holder.bind(beer)
        if (favoriteSelectedList.isEmpty()) {
            holder.ibFavorite.setImageResource(R.drawable.ic_favorite_border)
        } else {
            if (isFavoriteSelected(position)) {
                holder.ibFavorite.setImageResource(R.drawable.ic_favorite)
            } else {
                holder.ibFavorite.setImageResource(R.drawable.ic_favorite_border)
            }
        }
        holder.ibFavorite.setOnClickListener {
            dialog.onVisibleDialog()
            it.isEnabled = false
            holder.clHolderEvent.isEnabled = false
            Handler(Looper.getMainLooper()).post {
                if (isFavoriteSelected(position)) {
                    dataBase?.deleteFavoriteBeer(beer.id!!)
                } else {
                    dataBase?.insertFavoriteBeer(beer)
                }
                addPosToFavIcon(position)
                notifyDataSetChanged()
                dialog.onGoneDialog()
                it.isEnabled = true
                holder.clHolderEvent.isEnabled = true
            }
        }
        holder.clHolderEvent.setOnClickListener {
            beerId(beer.id!!)
        }
        Handler(Looper.getMainLooper()).post {
            dialog.onGoneDialog()
        }
    }

    private fun addPosToFavIcon(position: Int){
        if (favoriteSelectedList.isEmpty()) {
            favoriteSelectedList.add(position)
        } else {
            if (isFavoriteSelected(position)) {
                favoriteSelectedList.remove(position)
            } else {
                favoriteSelectedList.add(position)
            }
        }
    }

    private fun isFavoriteSelected(position: Int): Boolean{
        var isInList = false
        favoriteSelectedList.forEach {
            if (it == position) {
                isInList = true
            }
        }
        return isInList
    }

    override fun getItemCount(): Int {
        return listBeers.size
    }
}