package com.example.trabalhofinalpdm.telas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.trabalhofinalpdm.R
import com.example.trabalhofinalpdm.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn1.setOnClickListener(){
            val intent = Intent(this, ClienteActivity::class.java)
            startActivity(intent)
        }
        binding.btn2.setOnClickListener(){
            val intent = Intent(this, ProdutoActivity::class.java)
            startActivity(intent)
        }
        binding.btn3.setOnClickListener(){
            val intent = Intent(this, PedidoActivity::class.java)
            startActivity(intent)
        }
    }
}