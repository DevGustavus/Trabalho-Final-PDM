package com.example.trabalhofinalpdm.DAO

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.trabalhofinalpdm.classes.Cliente
import com.example.trabalhofinalpdm.classes.Pedido
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson

class PedidoDAO {

    private val referencia: DatabaseReference = FirebaseDatabase.getInstance().getReference("pedidos")

    fun inserirPedido(pedido: Pedido) {
        // Gera um novo ID automático usando push()
        val novoId = referencia.push().key

        /*
        // Converte o ID de String para Int (se possível)
        val idInt = novoId?.toIntOrNull()
        */

        // Define o ID gerado para o pedido
        pedido.id = novoId

        // Insere o pedido no banco de dados
        novoId?.let {
            referencia.child(it).setValue(pedido)
                .addOnSuccessListener {
                    Log.d("PedidoDAO", "Pedido inserido com sucesso. ID: $novoId")
                }
                .addOnFailureListener { exception ->
                    Log.e("PedidoDAO", "Erro ao inserir pedido. ID: $novoId", exception)
                }
        }
    }

    fun excluirPedido(idPedido: String) {
        /*
        // Converte o ID para String
        val idString = idPedido.toString()
        */

        // Remove o pedido do banco de dados
        referencia.child(idPedido).removeValue()
            .addOnSuccessListener {
                Log.d("PedidoDAO", "Pedido removido com sucesso. ID: $idPedido")
            }
            .addOnFailureListener { exception ->
                Log.e("PedidoDAO", "Erro ao remover pedido. ID: $idPedido", exception)
            }
    }

    fun mostrarPedidos(){
        //Apresentação dos elementos.
        val listaPedidos = ArrayList<Pedido>()
        referencia.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot)
            {
                if (snapshot.exists())
                {
                    var gson = Gson()
                    for (i in snapshot.children)
                    {
                        val json = gson.toJson(i.value)
                        val pedido = gson.fromJson(json, Pedido::class.java)
                        Log.i("Teste", "-------------------")
                        Log.i("Teste", "Id: ${pedido.id}")
                        Log.i("Teste", "Cliente: ${pedido.cliente}")
                        Log.i("Teste", "Data: ${pedido.data}")
                        Log.i("Teste", "Produtos: ${pedido.produtos}")
                        Log.i("Teste", "Quantidade: ${pedido.quantidade}")
                        Log.i("Teste", "-------------------")
                        listaPedidos.add(Pedido(pedido.id,pedido.cliente,pedido.data,pedido.produtos,pedido.quantidade))
                    }
                    Log.i("Teste", "Array: ${listaPedidos}")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.i("MENSAGEM", "Erro: $error")
            }
        })
    }

    fun obterListaPedidos(): ArrayList<Pedido> {
        //Apresentação dos elementos.
        val listaPedidos = ArrayList<Pedido>()
        referencia.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot)
            {
                if (snapshot.exists())
                {
                    var gson = Gson()
                    for (i in snapshot.children)
                    {
                        val json = gson.toJson(i.value)
                        val pedido = gson.fromJson(json, Pedido::class.java)
                        Log.i("Teste", "-------------------")
                        Log.i("Teste", "Id: ${pedido.id}")
                        Log.i("Teste", "Cliente: ${pedido.cliente}")
                        Log.i("Teste", "Data: ${pedido.data}")
                        Log.i("Teste", "Produtos: ${pedido.produtos}")
                        Log.i("Teste", "Quantidade: ${pedido.quantidade}")
                        Log.i("Teste", "-------------------")
                        listaPedidos.add(Pedido(pedido.id,pedido.cliente,pedido.data,pedido.produtos,pedido.quantidade))
                    }
                    Log.i("Teste", "Array: ${listaPedidos}")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.i("MENSAGEM", "Erro: $error")
            }
        })
        return listaPedidos
    }

    fun atualizarPedido(pedido: Pedido) {
        Log.i("Atualizar", "-------------------")
        Log.i("Atualizar", "Id: ${pedido.id}")
        Log.i("Atualizar", "cliente: ${pedido.cliente}")
        Log.i("Atualizar", "data: ${pedido.data}")
        Log.i("Atualizar", "produtos: ${pedido.produtos}")
        Log.i("Atualizar", "quantidade: ${pedido.quantidade}")
        Log.i("Atualizar", "-------------------")

        // Atualiza os dados no Firebase
        referencia.child(pedido.id.toString()).setValue(pedido)
            .addOnSuccessListener {
                // A operação foi bem-sucedida
                Log.d("PedidoDAO", "Pedido atualizado com sucesso")
            }
            .addOnFailureListener { e ->
                // A operação falhou
                Log.e("PedidoDAO", "Erro ao atualizar pedido", e)
            }
    }
}