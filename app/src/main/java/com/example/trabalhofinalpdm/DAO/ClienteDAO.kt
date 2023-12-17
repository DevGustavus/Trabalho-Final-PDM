package com.example.trabalhofinalpdm.DAO

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.trabalhofinalpdm.classes.Cliente
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson

class ClienteDAO {

    private val referencia: DatabaseReference = FirebaseDatabase.getInstance().getReference("clientes")

    fun inserirCliente(cliente: Cliente) {
        // Gera um novo ID automático usando push()
        val novoId = referencia.push().key

        // Converte o ID de String para Int (se possível)
        val idInt = novoId?.toIntOrNull()

        // Define o ID gerado para o cliente
        cliente.id = idInt

        // Insere o cliente no banco de dados
        novoId?.let {
            referencia.child(it).setValue(cliente)
                .addOnSuccessListener {
                    Log.d("ClienteDAO", "Cliente inserido com sucesso. ID: $idInt")
                }
                .addOnFailureListener { exception ->
                    Log.e("ClienteDAO", "Erro ao inserir cliente. ID: $idInt", exception)
                }
        }
    }

    fun excluirCliente(idCliente: Int) {
        // Converte o ID para String
        val idString = idCliente.toString()

        // Remove o cliente do banco de dados
        referencia.child(idString).removeValue()
            .addOnSuccessListener {
                Log.d("ClienteDAO", "Cliente removido com sucesso. ID: $idCliente")
            }
            .addOnFailureListener { exception ->
                Log.e("ClienteDAO", "Erro ao remover cliente. ID: $idCliente", exception)
            }
    }

    fun mostrarClientes(){
        //Apresentação dos elementos.
        val listaClientes = ArrayList<Cliente>()
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
                        val cliente = gson.fromJson(json, Cliente::class.java)
                        Log.i("Teste", "-------------------")
                        Log.i("Teste", "Id: ${cliente.id}")
                        Log.i("Teste", "CPF: ${cliente.cpf}")
                        Log.i("Teste", "Nome: ${cliente.nome}")
                        Log.i("Teste", "Telefone: ${cliente.telefone}")
                        Log.i("Teste", "Endereco: ${cliente.endereco}")
                        Log.i("Teste", "-------------------")
                        listaClientes.add(Cliente(cliente.id,cliente.cpf,cliente.nome,cliente.telefone,cliente.endereco))
                    }
                    Log.i("Teste", "Array: ${listaClientes}")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.i("MENSAGEM", "Erro: $error")
            }
        })
    }

    fun obterListaClientes(): ArrayList<Cliente> {
        //Apresentação dos elementos.
        val listaClientes = ArrayList<Cliente>()
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
                        val cliente = gson.fromJson(json, Cliente::class.java)
                        Log.i("Teste", "-------------------")
                        Log.i("Teste", "Id: ${cliente.id}")
                        Log.i("Teste", "CPF: ${cliente.cpf}")
                        Log.i("Teste", "Nome: ${cliente.nome}")
                        Log.i("Teste", "Telefone: ${cliente.telefone}")
                        Log.i("Teste", "Endereco: ${cliente.endereco}")
                        Log.i("Teste", "-------------------")
                        listaClientes.add(Cliente(cliente.id,cliente.cpf,cliente.nome,cliente.telefone,cliente.endereco))
                    }
                    Log.i("Teste", "Array: ${listaClientes}")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.i("MENSAGEM", "Erro: $error")
            }
        })
        return listaClientes
    }

    fun atualizarCliente(cliente: Cliente) {
        // Verifica se o cliente tem um ID válido
        val clienteId = cliente.id ?: return

        // Cria um mapa com os dados do cliente que serão atualizados
        val atualizacaoCliente = mapOf(
            "cpf" to cliente.cpf,
            "nome" to cliente.nome,
            "telefone" to cliente.telefone,
            "endereco" to cliente.endereco
        )

        // Atualiza os dados no Firebase
        referencia.child(clienteId.toString()).updateChildren(atualizacaoCliente)
            .addOnSuccessListener {
                // A operação foi bem-sucedida
                Log.d("ClienteDAO", "Cliente atualizado com sucesso")
            }
            .addOnFailureListener { e ->
                // A operação falhou
                Log.e("ClienteDAO", "Erro ao atualizar cliente", e)
            }
    }

}