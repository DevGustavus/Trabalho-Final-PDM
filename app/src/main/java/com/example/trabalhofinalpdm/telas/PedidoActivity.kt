package com.example.trabalhofinalpdm.telas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.trabalhofinalpdm.DAO.ClienteDAO
import com.example.trabalhofinalpdm.DAO.PedidoDAO
import com.example.trabalhofinalpdm.DAO.ProdutoDAO
import com.example.trabalhofinalpdm.R
import com.example.trabalhofinalpdm.classes.Cliente
import com.example.trabalhofinalpdm.classes.Pedido
import com.example.trabalhofinalpdm.classes.Produto
import com.example.trabalhofinalpdm.databinding.ActivityPedidoBinding

class PedidoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPedidoBinding

    private var pos: Int = 0

    private lateinit var cliente: Cliente
    private lateinit var produto: Produto
    private lateinit var pedido: Pedido
    private var quantidade: Int = 0
    private lateinit var produtos: ArrayList<Produto>
    private lateinit var quantidades: ArrayList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPedidoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.spinnerDeletar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Agora você pode acessar a posição sem problemas
                Log.d("Spinner", "Posição selecionada: $position")
                pos = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Tratamento quando nada é selecionado
            }
        }

        binding.spinnerAlterar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Agora você pode acessar a posição sem problemas
                Log.d("Spinner", "Posição selecionada: $position")
                pos = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Tratamento quando nada é selecionado
            }
        }

        val dao = PedidoDAO()
        val daoCliente = ClienteDAO()
        val daoProduto = ProdutoDAO()

        window.statusBarColor = getColor(R.color.black)

        var listaCliente = daoCliente.obterListaClientes()
        var listaProduto = daoProduto.obterListaProdutos()

        binding.btn1.setOnClickListener(){
            binding.layoutButtons.visibility = View.GONE
            binding.popUp.visibility = View.VISIBLE
            binding.layoutInserir.visibility = View.VISIBLE

            binding.adicionarCliente.setOnClickListener(){
                binding.spinnerClienteInserir.visibility = View.VISIBLE
                binding.adicionarCliente2.visibility = View.VISIBLE
                binding.adicionarCliente.visibility = View.GONE

                binding.adicionarCliente2.setOnClickListener(){
                    cliente = listaCliente[Math.abs(pos)]
                    binding.cliente.text = cliente.toString()
                    binding.cliente.visibility = View.VISIBLE
                    binding.spinnerClienteInserir.visibility = View.GONE
                    binding.adicionarCliente2.visibility = View.GONE
                }
            }

            binding.adicionarProduto.setOnClickListener(){
                binding.spinnerProdutoInserir.visibility = View.VISIBLE
                binding.quantidadeProduto.visibility = View.VISIBLE
                binding.adicionarCliente2.visibility = View.VISIBLE
                binding.adicionarProduto.visibility = View.GONE

                binding.adicionarProduto2.setOnClickListener(){
                    produto = listaProduto[Math.abs(pos)]
                    produtos.add(produto)
                    val listView = binding.produtos

                    val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, produtos)

                    adapter.notifyDataSetChanged()

                    listView.adapter = adapter
                    binding.produtos.visibility = View.VISIBLE
                    binding.spinnerClienteInserir.visibility = View.GONE
                    quantidade = binding.quantidadeProduto.text.toString().toInt()
                    quantidades.add(quantidade)
                    binding.quantidadeProduto.visibility = View.GONE
                    binding.adicionarProduto2.visibility = View.GONE
                    binding.adicionarProduto.visibility = View.VISIBLE
                }
            }

            binding.btnInserir.setOnClickListener(){
                binding.layoutInserir.visibility = View.GONE

                    try {
                        dao.inserirPedido(Pedido(null, cliente.id, "11/11/2024", produtos, quantidades))
                        limparCamposInserir()
                        messagePopUp("Inserido com sucesso!")
                    }catch (e: Exception){
                        limparCamposInserir()
                        messagePopUp("Erro ao inserir.\n"+e.message)
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

            Log.i("TESTEEE", lista.toString())

            binding.btnDeletar.setOnClickListener(){

                binding.layoutDeletar.visibility = View.GONE

                val cliente = lista[Math.abs(pos)]

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

                val cliente = lista[Math.abs(pos)]

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

    private fun listaSpinner(i: Int, lista: ArrayList<Pedido>){


        //i = 1 -> deletar
        if (i == 1){

            val adapter = ArrayAdapter<Pedido>(this, android.R.layout.simple_spinner_item, lista)

            adapter.setDropDownViewResource(R.layout.spinner_item_layout)

            adapter.notifyDataSetChanged()

            Log.i("lista", lista.toString())

            binding.spinnerDeletar.adapter = adapter
            adapter.notifyDataSetChanged()

        }

        //i = 2 -> alterar
        if (i == 2){

            val adapter = ArrayAdapter<Pedido>(this, android.R.layout.simple_spinner_item, lista)

            adapter.setDropDownViewResource(R.layout.spinner_item_layout)

            adapter.notifyDataSetChanged()

            Log.i("lista", lista.toString())

            binding.spinnerAlterar.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    private fun listarSpinnerInserir(i: Int){
        if (i == 1){

            val dao = ClienteDAO()

            val lista = dao.obterListaClientes()

            val adapter = ArrayAdapter<Cliente>(this, android.R.layout.simple_spinner_item, lista)

            adapter.setDropDownViewResource(R.layout.spinner_item_layout)

            adapter.notifyDataSetChanged()

            Log.i("lista", lista.toString())

            binding.spinnerClienteInserir.adapter = adapter
            adapter.notifyDataSetChanged()
        }

        if (i == 2){

            val dao = ProdutoDAO()

            val lista = dao.obterListaProdutos()

            val adapter = ArrayAdapter<Produto>(this, android.R.layout.simple_spinner_item, lista)

            adapter.setDropDownViewResource(R.layout.spinner_item_layout)

            adapter.notifyDataSetChanged()

            Log.i("lista", lista.toString())

            binding.spinnerProdutoInserir.adapter = adapter
            adapter.notifyDataSetChanged()
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