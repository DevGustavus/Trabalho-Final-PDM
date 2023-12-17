package com.example.trabalhofinalpdm.telas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.trabalhofinalpdm.R
import com.example.trabalhofinalpdm.databinding.ActivityPedidoBinding

class PedidoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPedidoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPedidoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}