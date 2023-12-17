package com.example.trabalhofinalpdm.DAO

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.trabalhofinalpdm.classes.Pedido
import com.example.trabalhofinalpdm.classes.Produto
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson

class ProdutoDAO {

    private val referencia: DatabaseReference = FirebaseDatabase.getInstance().getReference("produtos")

    fun inserirProduto(produto: Produto) {
        // Gera um novo ID automático usando push()
        val novoId = referencia.push().key

        // Converte o ID de String para Int (se possível)
        val idInt = novoId?.toIntOrNull()

        // Define o ID gerado para o produto
        produto.id = idInt

        // Insere o produto no banco de dados
        novoId?.let {
            referencia.child(it).setValue(produto)
                .addOnSuccessListener {
                    Log.d("ProdutoDAO", "Produto inserido com sucesso. ID: $idInt")
                }
                .addOnFailureListener { exception ->
                    Log.e("ProdutoDAO", "Erro ao inserir produto. ID: $idInt", exception)
                }
        }
    }

    fun excluirProduto(idProduto: Int) {
        // Converte o ID para String
        val idString = idProduto.toString()

        // Remove o produto do banco de dados
        referencia.child(idString).removeValue()
            .addOnSuccessListener {
                Log.d("ProdutoDAO", "Produto removido com sucesso. ID: $idProduto")
            }
            .addOnFailureListener { exception ->
                Log.e("ProdutoDAO", "Erro ao remover produto. ID: $idProduto", exception)
            }
    }

    fun mostrarProdutos(){
        //Apresentação dos elementos.
        val listaProdutos = ArrayList<Produto>()
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
                        val produto = gson.fromJson(json, Produto::class.java)
                        Log.i("Teste", "-------------------")
                        Log.i("Teste", "Id: ${produto.id}")
                        Log.i("Teste", "Descricao: ${produto.descricao}")
                        Log.i("Teste", "Valor: ${produto.valor}")
                        Log.i("Teste", "-------------------")
                        listaProdutos.add(Produto(produto.id,produto.descricao,produto.valor))
                    }
                    Log.i("Teste", "Array: ${listaProdutos}")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.i("MENSAGEM", "Erro: $error")
            }
        })
    }

    fun obterListaProdutos(): ArrayList<Produto> {
        //Apresentação dos elementos.
        val listaProdutos = ArrayList<Produto>()
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
                        val produto = gson.fromJson(json, Produto::class.java)
                        Log.i("Teste", "-------------------")
                        Log.i("Teste", "Id: ${produto.id}")
                        Log.i("Teste", "Descricao: ${produto.descricao}")
                        Log.i("Teste", "Valor: ${produto.valor}")
                        Log.i("Teste", "-------------------")
                        listaProdutos.add(Produto(produto.id,produto.descricao,produto.valor))
                    }
                    Log.i("Teste", "Array: ${listaProdutos}")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.i("MENSAGEM", "Erro: $error")
            }
        })

        return listaProdutos
    }

    fun atualizarProduto(produto: Produto) {
        // Verifica se o produto tem um ID válido
        val produtoId = produto.id ?: return

        // Cria um mapa com os dados do produto que serão atualizados
        val atualizacaoProduto = mapOf(
            "descricao" to produto.descricao,
            "valor" to produto.valor
        )

        // Atualiza os dados no Firebase
        referencia.child(produtoId.toString()).updateChildren(atualizacaoProduto)
            .addOnSuccessListener {
                // A operação foi bem-sucedida
                Log.d("ProdutoDAO", "Produto atualizado com sucesso")
            }
            .addOnFailureListener { e ->
                // A operação falhou
                Log.e("ProdutoDAO", "Erro ao atualizar produto", e)
            }
    }
}