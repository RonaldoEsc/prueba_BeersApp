package net.hrec.pruebatecnica.usecases.detalle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import net.hrec.pruebatecnica.R
import net.hrec.pruebatecnica.databinding.FragmentDetalleBeerBinding
import net.hrec.pruebatecnica.model.DetailBeerResponse
import net.hrec.pruebatecnica.util.UtilString.Companion.convertDoubleOrIntToString

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
        initView()
    }

    private fun initView() {
        binding.tbTitle.text = "Beer Description"
        binding.progressCircular.visibility = View.VISIBLE
        binding.tbButtonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel = ViewModelProvider(this)[DetalleBeerViewModel::class.java]
        viewModel.getBeers(id?.id ?: -1)

        viewModel.beersList.observe(viewLifecycleOwner) { beer ->
            loadViewModel(beer)
        }
    }

    private fun loadViewModel(beer: DetailBeerResponse) {
        // primera parte "head"
        Picasso.get().load(beer.imageUrl).placeholder(R.drawable.progress_animation).into(binding.imgBeer)
        binding.tvBeerName.text = beer.name
        binding.tvBeerYeast.text = beer.ingredients?.yeast

        binding.tvABV.text = convertDoubleOrIntToString(beer.abv)
        binding.tvFG.text = convertDoubleOrIntToString(beer.target_fg)
        binding.tvIBU.text = convertDoubleOrIntToString(beer.ibu)
        binding.tvOG.text = convertDoubleOrIntToString(beer.target_og)

        // segunda parte "body"
        binding.tvTagLine.text = beer.tagline
        binding.tvBrewed.text = beer.first_brewed
        binding.tvBrewedTips.text = beer.brewers_tips

        var llm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.rvFoodPairing.layoutManager = llm
        binding.rvFoodPairing.adapter = FoodPairingAdapter(beer.food_pairing)

        llm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.rvHopsTips.layoutManager = llm
        binding.rvHopsTips.adapter = IngredientsAdapter(beer.ingredients, false)

        llm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.rvMaltTips.layoutManager = llm
        binding.rvMaltTips.adapter = IngredientsAdapter(beer.ingredients, true)

        binding.progressCircular.visibility = View.GONE
    }
}