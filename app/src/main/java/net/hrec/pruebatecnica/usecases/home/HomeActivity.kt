package net.hrec.pruebatecnica.usecases.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import net.hrec.pruebatecnica.databinding.ActivityHomeBinding
import net.hrec.pruebatecnica.usecases.common.interfaces.BackEvent
import net.hrec.pruebatecnica.usecases.common.interfaces.NavEventListener
import kotlin.system.exitProcess

class HomeActivity : AppCompatActivity(), NavEventListener {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onNavigateChangeEvent(action: NavDirections) {
        val navController = binding.navHostFragment.findNavController()
        navController.navigate(action)
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("Aviso")
            .setMessage("¿Deseas salir de la aplicacion?")
            .setPositiveButton("Si") { _, _ ->
                finish()
                exitProcess(0)
            }
            .setNegativeButton("no") { _, _ -> }
            .create().show()
    }
}