package net.hrec.pruebatecnica.usecases.detalle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import net.hrec.pruebatecnica.R
import net.hrec.pruebatecnica.databinding.FragmentDetalleBeerBinding
import net.hrec.pruebatecnica.usecases.common.BeersListAdapter
import net.hrec.pruebatecnica.usecases.home.HomeViewModel

class DetalleBeerFragment : Fragment() {
    private lateinit var binding: FragmentDetalleBeerBinding
    private lateinit var viewModel: DetalleBeerViewModel
    private var id: DetalleBeerFragmentArgs? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = DetalleBeerFragmentArgs.fromBundle(arguments!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetalleBeerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[DetalleBeerViewModel::class.java]
        viewModel.getBeers(id?.id ?: -1)

        viewModel.beersList.observe(viewLifecycleOwner) { beer ->
            Picasso.get().load(beer.imageUrl).placeholder(R.drawable.progress_animation).into(binding.imgBeer)
            binding.tvBeerName.text = beer.name
        }
    }
}