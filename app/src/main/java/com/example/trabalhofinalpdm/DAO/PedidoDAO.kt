package com.example.trabalhofinalpdm.DAO

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.trabalhofinalpdm.classes.Pedido
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PedidoDAO {

    private val referencia: DatabaseReference = FirebaseDatabase.getInstance().getReference("pedidos")

    fun inserirPedido(pedido: Pedido) {
        // Gera um novo ID automático usando push()
        val novoId = referencia.push().key

        // Converte o ID de String para Int (se possível)
        val idInt = novoId?.toIntOrNull()

        // Define o ID gerado para o pedido
        pedido.id = idInt

        // Insere o pedido no banco de dados
        novoId?.let {
            referencia.child(it).setValue(pedido)
                .addOnSuccessListener {
                    Log.d("PedidoDAO", "Pedido inserido com sucesso. ID: $idInt")
                }
                .addOnFailureListener { exception ->
                    Log.e("PedidoDAO", "Erro ao inserir pedido. ID: $idInt", exception)
                }
        }
    }

    fun excluirPedido(idPedido: Int) {
        // Converte o ID para String
        val idString = idPedido.toString()

        // Remove o pedido do banco de dados
        referencia.child(idString).removeValue()
            .addOnSuccessListener {
                Log.d("PedidoDAO", "Pedido removido com sucesso. ID: $idPedido")
            }
            .addOnFailureListener { exception ->
                Log.e("PedidoDAO", "Erro ao remover pedido. ID: $idPedido", exception)
            }
    }

    fun mostrarPedidosLog() {
        referencia.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listaPedidos = mutableListOf<Pedido>()

                for (pedidoSnapshot in snapshot.children) {
                    val pedido = pedidoSnapshot.getValue(Pedido::class.java)
                    pedido?.let {
                        listaPedidos.add(it)
                    }
                }

                Log.d("PedidoDAO", "Lista de Pedidos:")
                for (pedido in listaPedidos) {
                    Log.d("PedidoDAO", "ID: ${pedido.id}, Cliente: ${pedido.cliente.nome}, Data: ${pedido.data}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("PedidoDAO", "Erro ao ler pedidos do Firebase", error.toException())
            }
        })
    }

    fun obterListaPedidos(): MutableLiveData<List<Pedido>> {
        val listaPedidosLiveData = MutableLiveData<List<Pedido>>()

        referencia.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listaPedidos = mutableListOf<Pedido>()

                for (pedidoSnapshot in snapshot.children) {
                    val pedido = pedidoSnapshot.getValue(Pedido::class.java)
                    pedido?.let {
                        listaPedidos.add(it)
                    }
                }

                // Atualiza o LiveData com a lista de pedidos
                listaPedidosLiveData.value = listaPedidos
            }

            override fun onCancelled(error: DatabaseError) {
                // Se ocorrer um erro, você pode lidar com isso aqui
            }
        })

        return listaPedidosLiveData
    }

    fun atualizarPedido(pedido: Pedido) {
        // Verifica se o pedido tem um ID válido
        val pedidoId = pedido.id ?: return

        // Cria um mapa com os dados do pedido que serão atualizados
        val atualizacaoPedido = mapOf(
            "cliente" to mapOf(
                "id" to pedido.cliente.id,
                "cpf" to pedido.cliente.cpf,
                "nome" to pedido.cliente.nome,
                "telefone" to pedido.cliente.telefone,
                "endereco" to pedido.cliente.endereco
            ),
            "data" to pedido.data,
            "itensPedido" to pedido.itensPedido
        )

        // Atualiza os dados no Firebase
        referencia.child(pedidoId.toString()).updateChildren(atualizacaoPedido)
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