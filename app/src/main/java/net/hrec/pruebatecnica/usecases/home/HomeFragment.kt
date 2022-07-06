package net.hrec.pruebatecnica.usecases.home

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import net.hrec.pruebatecnica.databinding.FragmentHomeBinding
import net.hrec.pruebatecnica.databinding.ProgressAlertBinding
import net.hrec.pruebatecnica.usecases.common.interfaces.DialogEvent
import net.hrec.pruebatecnica.usecases.common.interfaces.NavEventListener
import net.hrec.pruebatecnica.util.Constants.Companion.PREF_NAME

class HomeFragment : Fragment(), DialogEvent {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var progress: AlertDialog

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
        onVisibleDialog()
    }

    override fun onResume() {
        super.onResume()
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        val llm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.rvBeers.layoutManager = llm
        binding.rvBeers.adapter = BeersListAdapter(this){ id ->
            val navEvent: NavEventListener = activity as HomeActivity
            val event = HomeFragmentDirections.actionHomeFragmentToDetalleBeerFragment(id)
            navEvent.onNavigateChangeEvent(event)
            onGoneDialog()
        }

        binding.tbGoToFavorites.setOnClickListener {
            val navEvent: NavEventListener = activity as HomeActivity
            val event = HomeFragmentDirections.actionHomeFragmentToFavoritesFragment()
            navEvent.onNavigateChangeEvent(event)
        }

        binding.tbLogOut.setOnClickListener {
            val sharedPref = context?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val editor = sharedPref?.edit()
            editor?.clear()
            editor?.apply()
            val navEvent: NavEventListener = activity as HomeActivity
            val event = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
            navEvent.onNavigateChangeEvent(event)
        }

        viewModel.getBeers(1, 80)

        viewModel.beersList.observe(viewLifecycleOwner) { list ->
            (binding.rvBeers.adapter as BeersListAdapter).setData(list, activity!!.baseContext)
        }

        viewModel.closeSession.observe(viewLifecycleOwner) { isClosed ->
            if (isClosed) {
                findNavController().popBackStack()
            }
        }
    }

    override fun onVisibleDialog() {
        progress = AlertDialog.Builder(context)
            .setView(ProgressAlertBinding.inflate(layoutInflater).root)
            .setCancelable(false)
            .show()
    }

    override fun onGoneDialog() {
        progress.cancel()
    }
}