package net.hrec.pruebatecnica.usecases.detalle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import net.hrec.pruebatecnica.databinding.ActivityDetalleBeerBinding

class DetalleBeerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetalleBeerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleBeerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}