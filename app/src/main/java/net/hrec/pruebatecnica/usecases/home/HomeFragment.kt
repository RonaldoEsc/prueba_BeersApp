package net.hrec.pruebatecnica.usecases.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import net.hrec.pruebatecnica.databinding.FragmentHomeBinding
import net.hrec.pruebatecnica.databinding.FragmentLoginBinding
import net.hrec.pruebatecnica.usecases.common.BeersListAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        val llm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.rvBeers.layoutManager = llm
        binding.rvBeers.adapter = BeersListAdapter{ id ->

        }

        viewModel.getBeers(1, 80)

        viewModel.beersList.observe(viewLifecycleOwner) { list ->
            (binding.rvBeers.adapter as BeersListAdapter).setData(list.toMutableList())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment()
    }
}