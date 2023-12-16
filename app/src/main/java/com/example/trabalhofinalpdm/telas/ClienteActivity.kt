package com.example.trabalhofinalpdm.telas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.example.trabalhofinalpdm.DAO.ClienteDAO
import com.example.trabalhofinalpdm.R
import com.example.trabalhofinalpdm.classes.Cliente
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

                val cpf = binding.cpfInserir.text.toString()
                val nome = binding.nomeInserir.text.toString()
                val telefone = binding.telefoneInserir.text.toString()
                val endereco = binding.enderecoInserir.text.toString()

                if (cpf == "" || nome == "" || telefone == "" || endereco == ""){
                    limparCamposInserir()
                    messagePopUp("Preencha todos os campos!")
                    binding.popUp.postDelayed({binding.btn1.performClick()},1500)
                }else {
                    try {
                        dao.inserirCliente(Cliente(null, cpf, nome, telefone, endereco))
                        limparCamposInserir()
                        messagePopUp("Inserido com sucesso!")
                    }catch (e: Exception){
                        limparCamposInserir()
                        messagePopUp("Erro ao inserir.\n"+e.message)
                    }
                }
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

            val listaMutable = dao.obterListaClientes()


            //val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lista)

//            listView.adapter = adapter

            binding.btnBackLista.setOnClickListener() {
                binding.layoutListar.visibility = View.GONE
                binding.popUp.visibility = View.GONE
            }
        }

        binding.btn3.setOnClickListener(){
            binding.popUp.visibility = View.VISIBLE
            binding.layoutDeletar.visibility = View.VISIBLE

            listaSpinner(1)

            binding.btnDeletar.setOnClickListener(){

                binding.layoutDeletar.visibility = View.GONE

                val cliente = binding.spinnerDeletar.selectedItem

                try {
                    //dao.excluirCliente(cliente.id)
                    messagePopUp("Cliente excluído!")
                }catch (e: Exception){
                    messagePopUp("Erro ao deletar.\n"+e.message)
                }
            }

            binding.btnCancelarDeletar.setOnClickListener(){
                binding.layoutDeletar.visibility = View.GONE
                messagePopUp("Exclusão Cancelada!")
            }

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

    private fun listaSpinner(i: Int){

        val dao = ClienteDAO()

        //i = 1 -> deletar
        if (i == 1){
            val lista = dao.obterListaClientes()

            //val adapter = ArrayAdapter<Cliente>(this, android.R.layout.simple_spinner_item, lista)

            //adapter.setDropDownViewResource(R.layout.spinner_item_layout)

            //binding.spinnerDeletar.adapter = adapter
        }

        //i = 2 -> alterar
        if (i == 2){

        }
    }

}