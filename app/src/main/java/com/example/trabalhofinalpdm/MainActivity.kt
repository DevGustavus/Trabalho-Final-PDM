package com.example.trabalhofinalpdm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.trabalhofinalpdm.databinding.ActivityMainBinding
import com.example.trabalhofinalpdm.telas.MenuActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }
}