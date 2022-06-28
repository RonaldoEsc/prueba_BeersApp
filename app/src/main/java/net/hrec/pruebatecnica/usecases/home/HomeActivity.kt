package net.hrec.pruebatecnica.usecases.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import net.hrec.pruebatecnica.databinding.ActivityHomeBinding
import net.hrec.pruebatecnica.usecases.common.BeersListAdapter

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val llm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.rvBeers.layoutManager = llm
        binding.rvBeers.adapter = BeersListAdapter{ id ->

        }

        viewModel.getBeers(1, 80)

        viewModel.beersList.observe(this) { list ->
            (binding.rvBeers.adapter as BeersListAdapter).setData(list.toMutableList())
        }

    }
}