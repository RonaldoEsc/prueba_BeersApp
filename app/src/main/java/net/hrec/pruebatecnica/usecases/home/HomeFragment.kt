package net.hrec.pruebatecnica.usecases.home

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import net.hrec.pruebatecnica.databinding.FragmentHomeBinding
import net.hrec.pruebatecnica.databinding.ProgressAlertBinding
import net.hrec.pruebatecnica.usecases.common.interfaces.DialogEvent
import net.hrec.pruebatecnica.usecases.common.interfaces.NavEventListener

class HomeFragment : Fragment(), DialogEvent {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var progress: AlertDialog

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

        viewModel.getBeers(1, 80)

        viewModel.beersList.observe(viewLifecycleOwner) { list ->
            (binding.rvBeers.adapter as BeersListAdapter).setData(list)
            onGoneDialog()
        }
    }

    override fun onVisibleDialog() {
        progress = AlertDialog.Builder(context)
            .setView(ProgressAlertBinding.inflate(layoutInflater).root)
            .setCancelable(false)
            .create()
    }

    override fun onGoneDialog() {
        progress.cancel()
    }
}