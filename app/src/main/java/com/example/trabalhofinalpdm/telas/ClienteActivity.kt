package com.example.trabalhofinalpdm.telas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.example.trabalhofinalpdm.DAO.ClienteDAO
import com.example.trabalhofinalpdm.R
import com.example.trabalhofinalpdm.databinding.ActivityClienteBinding

class ClienteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClienteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = ClienteDAO()

        window.statusBarColor = getColor(R.color.black)

        binding.btn1.setOnClickListener(){
            binding.popUp.visibility = View.VISIBLE
            binding.layoutInserir.visibility = View.VISIBLE

            binding.btnInserir.setOnClickListener(){
                binding.layoutInserir.visibility = View.GONE
                limparCamposInserir()
                messagePopUp("Inserido com sucesso!")

            }

            binding.btnCancelarInserir.setOnClickListener(){
                binding.layoutInserir.visibility = View.GONE
                messagePopUp("Inserção Cancelada!")
                limparCamposInserir()

            }

        }

        binding.btn2.setOnClickListener(){
            binding.popUp.visibility = View.VISIBLE
            binding.layoutListar.visibility = View.VISIBLE

            val listView = binding.listViewMostrar

            val lista = dao.obterListaClientes()

            //val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lista)

//            listView.adapter = adapter

            binding.btnBackLista.setOnClickListener() {
                binding.layoutListar.visibility = View.GONE
                binding.popUp.visibility = View.GONE
            }
        }

        binding.btn3.setOnClickListener(){

        }

        binding.btn4.setOnClickListener(){

        }

        binding.btn5.setOnClickListener(){
            finish()
        }
    }

    private fun messagePopUp(message: String){
        binding.textMessage.text = message
        binding.message.visibility = View.VISIBLE
        binding.popUp.postDelayed({binding.message.visibility = View.GONE}, 1500)
        binding.popUp.postDelayed({binding.textMessage.text = ""}, 1500)
        binding.popUp.postDelayed({binding.popUp.visibility = View.GONE}, 1500)
    }

    private fun limparCamposInserir(){
        binding.cpfInserir.text.clear()
        binding.nomeInserir.text.clear()
        binding.telefoneInserir.text.clear()
        binding.enderecoInserir.text.clear()
    }

}