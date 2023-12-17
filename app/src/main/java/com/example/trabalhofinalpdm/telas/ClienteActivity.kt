package com.example.trabalhofinalpdm.telas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import com.example.trabalhofinalpdm.DAO.ClienteDAO
import com.example.trabalhofinalpdm.R
import com.example.trabalhofinalpdm.classes.Cliente
import com.example.trabalhofinalpdm.databinding.ActivityClienteBinding
import kotlin.math.log

class ClienteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClienteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = ClienteDAO()

        window.statusBarColor = getColor(R.color.black)

        var lista = dao.obterListaClientes()

        binding.btn1.setOnClickListener(){
            binding.layoutButtons.visibility = View.GONE
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
            binding.layoutButtons.visibility = View.GONE
            binding.popUp.visibility = View.VISIBLE
            binding.layoutListar.visibility = View.VISIBLE

            val listView = binding.listViewMostrar

            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lista)

            adapter.notifyDataSetChanged()

            Log.i("lista", lista.toString())

            listView.adapter = adapter

            binding.btnBackLista.setOnClickListener() {
                binding.layoutListar.visibility = View.GONE
                binding.popUp.visibility = View.GONE
                binding.layoutButtons.visibility = View.VISIBLE
            }
        }

        binding.btn3.setOnClickListener(){
            binding.layoutButtons.visibility = View.GONE
            binding.popUp.visibility = View.VISIBLE
            binding.layoutDeletar.visibility = View.VISIBLE

            listaSpinner(1, lista)

            binding.btnDeletar.setOnClickListener(){

                binding.layoutDeletar.visibility = View.GONE

                val pos = binding.spinnerAlterar.selectedItemPosition

                val cliente = lista[Math.abs(pos)] // TA PEGANDO VALOR NULL!!!!!
                Log.e("TESTE TESTE TESTE","${cliente.id}")

                try {
                    dao.excluirCliente(cliente.id!!)
                    messagePopUp("Cliente excluído!")
                }catch (e: Exception){
                    messagePopUp("Erro ao deletar.\n"+e.message)

                }
                lista.clear()
                lista = dao.obterListaClientes()

            }

            binding.btnCancelarDeletar.setOnClickListener(){
                binding.layoutDeletar.visibility = View.GONE
                messagePopUp("Exclusão Cancelada!")
            }

        }

        binding.btn4.setOnClickListener(){
            binding.layoutButtons.visibility = View.GONE
            binding.popUp.visibility = View.VISIBLE
            binding.layoutAlterar.visibility = View.VISIBLE

            listaSpinner(2, lista)

            binding.btnAlterar.setOnClickListener(){
                binding.layoutAlterar.visibility = View.GONE
                binding.layoutAlterar2.visibility = View.VISIBLE

                val pos = binding.spinnerAlterar.selectedItemPosition

                Log.i("alterar", pos.toString())

                Log.i("alterar", lista.toString())

                val cliente = lista[Math.abs(pos)]

                Log.i("alterar", cliente.nome)

                preencherCamposAlterar(cliente)

                val cpf = binding.cpfAlterar.text.toString()
                val nome = binding.nomeAlterar.text.toString()
                val telefone = binding.telefoneAlterar.text.toString()
                val endereco = binding.enderecoAlterar.text.toString()


                binding.btnAlterar2.setOnClickListener(){

                    binding.layoutAlterar2.visibility = View.GONE

                    if (cpf == "" || nome == "" || telefone == "" || endereco == ""){
                        limparCamposAlterar()
                        messagePopUp("Preencha todos os campos!")
                    }else {
                        try {
                            cliente.cpf = cpf
                            cliente.nome = nome
                            cliente.telefone = telefone
                            cliente.endereco = endereco
                            dao.atualizarCliente(cliente)
                            messagePopUp("Cliente Alterado!")
                        }catch (e: Exception){
                            messagePopUp("Erro ao alterar.\n"+e.message)
                        }
                        lista.clear()
                        lista = dao.obterListaClientes()

                    }
                }

                binding.btnCancelarAlterar2.setOnClickListener(){
                    binding.layoutAlterar2.visibility = View.GONE
                    limparCamposAlterar()
                    messagePopUp("Operação cancelada!")
                    binding.popUp.postDelayed({binding.btn4.performClick()},1500)
                }


            }

            binding.btnCancelarAlterar.setOnClickListener(){
                binding.layoutAlterar.visibility = View.GONE
                messagePopUp("Alteração cancelada!")
            }

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
        binding.layoutButtons.visibility = View.VISIBLE
    }

    private fun limparCamposInserir(){
        binding.cpfInserir.text.clear()
        binding.nomeInserir.text.clear()
        binding.telefoneInserir.text.clear()
        binding.enderecoInserir.text.clear()
    }

    private fun listaSpinner(i: Int, lista: ArrayList<Cliente>){

        val dao = ClienteDAO()

        //i = 1 -> deletar
        if (i == 1){

            val adapter = ArrayAdapter<Cliente>(this, android.R.layout.simple_spinner_item, lista)

            adapter.setDropDownViewResource(R.layout.spinner_item_layout)

            Log.i("lista", lista.toString())

            binding.spinnerDeletar.adapter = adapter
        }

        //i = 2 -> alterar
        if (i == 2){

            val adapter = ArrayAdapter<Cliente>(this, android.R.layout.simple_spinner_item, lista)

            adapter.setDropDownViewResource(R.layout.spinner_item_layout)

            Log.i("lista", lista.toString())

            binding.spinnerAlterar.adapter = adapter
        }
    }

    private fun limparCamposAlterar(){
        binding.cpfAlterar.text.clear()
        binding.nomeAlterar.text.clear()
        binding.telefoneAlterar.text.clear()
        binding.enderecoAlterar.text.clear()
    }

    private fun preencherCamposAlterar(cliente: Cliente){
        binding.cpfAlterar.setText(cliente.cpf)
        binding.nomeAlterar.setText(cliente.nome)
        binding.telefoneAlterar.setText(cliente.telefone)
        binding.enderecoAlterar.setText(cliente.endereco)
    }

}