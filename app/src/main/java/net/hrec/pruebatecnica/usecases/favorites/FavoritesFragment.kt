package net.hrec.pruebatecnica.usecases.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import net.hrec.pruebatecnica.databinding.FragmentFavoritesBinding
import net.hrec.pruebatecnica.usecases.common.interfaces.NavEventListener
import net.hrec.pruebatecnica.usecases.home.HomeActivity
import net.hrec.pruebatecnica.usecases.home.HomeFragmentDirections

class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: FavoritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]
        val llm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.rvBeers.layoutManager = llm
        binding.rvBeers.adapter = FavoritesAdapter { id ->
            val navEvent: NavEventListener = activity as HomeActivity
            val event = FavoritesFragmentDirections.actionFavoritesFragmentToDetalleBeerFragment(id)
            navEvent.onNavigateChangeEvent(event)
        }
        viewModel.getFavorites(context!!)

        binding.tbButtonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.favoriteBeersList.observe(viewLifecycleOwner) { list ->
            (binding.rvBeers.adapter as FavoritesAdapter).setData(list)
        }
    }
}