package com.example.trabalhofinalpdm.telas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import com.example.trabalhofinalpdm.DAO.ClienteDAO
import com.example.trabalhofinalpdm.DAO.ProdutoDAO
import com.example.trabalhofinalpdm.R
import com.example.trabalhofinalpdm.classes.Cliente
import com.example.trabalhofinalpdm.classes.Produto
import com.example.trabalhofinalpdm.databinding.ActivityProdutoBinding

class ProdutoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProdutoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProdutoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = ProdutoDAO()

        window.statusBarColor = getColor(R.color.black)

        var lista = dao.obterListaProdutos()

        binding.btn1.setOnClickListener(){
            binding.layoutButtons.visibility = View.GONE
            binding.popUp.visibility = View.VISIBLE
            binding.layoutInserir.visibility = View.VISIBLE

            binding.btnInserir.setOnClickListener(){
                binding.layoutInserir.visibility = View.GONE

                val descricao = binding.descricaoInserir.text.toString()
                val valor = binding.valorInserir.text.toString().toFloat()

                if (descricao == "" || valor.toString() == "" || valor <= 0){
                    limparCamposInserir()
                    messagePopUp("Preencha todos os campos!")
                    binding.popUp.postDelayed({binding.btn1.performClick()},1500)
                }else {
                    try {
                        dao.inserirProduto(Produto(null, descricao, valor))
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

                val produto = lista[Math.abs(pos)]

                try {
                    dao.excluirProduto(produto.id!!)
                    messagePopUp("Cliente excluído!")
                }catch (e: Exception){
                    messagePopUp("Erro ao deletar.\n"+e.message)

                }
                lista.clear()
                lista = dao.obterListaProdutos()

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

                val produto = lista[Math.abs(pos)]

                Log.i("alterar", produto.descricao)

                preencherCamposAlterar(produto)

                val descricao = binding.descricaoAlterar.text.toString()
                val valor = binding.valorAlterar.text.toString().toFloat()


                binding.btnAlterar2.setOnClickListener(){

                    binding.layoutAlterar2.visibility = View.GONE

                    if (descricao == "" || valor.toString() == "" || valor <= 0){
                        limparCamposAlterar()
                        messagePopUp("Preencha todos os campos!")
                    }else {
                        try {
                            produto.descricao = descricao
                            produto.valor = valor
                            dao.atualizarProduto(produto)
                            messagePopUp("Cliente Alterado!")
                        }catch (e: Exception){
                            messagePopUp("Erro ao alterar.\n"+e.message)
                        }
                        lista.clear()
                        lista = dao.obterListaProdutos()

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
        binding.descricaoInserir.text.clear()
        binding.valorInserir.text.clear()
    }

    private fun listaSpinner(i: Int, lista: ArrayList<Produto>){

        //i = 1 -> deletar
        if (i == 1){

            val adapter = ArrayAdapter<Produto>(this, android.R.layout.simple_spinner_item, lista)

            adapter.setDropDownViewResource(R.layout.spinner_item_layout)

            Log.i("lista", lista.toString())

            binding.spinnerDeletar.adapter = adapter
        }

        //i = 2 -> alterar
        if (i == 2){

            val adapter = ArrayAdapter<Produto>(this, android.R.layout.simple_spinner_item, lista)

            adapter.setDropDownViewResource(R.layout.spinner_item_layout)

            Log.i("lista", lista.toString())

            binding.spinnerAlterar.adapter = adapter
        }
    }

    private fun limparCamposAlterar(){
        binding.descricaoAlterar.text.clear()
        binding.valorAlterar.text.clear()
    }

    private fun preencherCamposAlterar(produto: Produto){
        binding.descricaoAlterar.setText(produto.descricao)
        binding.valorAlterar.setText(produto.valor.toString())
    }

}