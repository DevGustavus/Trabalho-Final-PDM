package com.example.trabalhofinalpdm.DAO

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.trabalhofinalpdm.classes.Produto
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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

    fun mostrarProdutosLog() {
        referencia.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listaProdutos = mutableListOf<Produto>()

                for (produtoSnapshot in snapshot.children) {
                    val produto = produtoSnapshot.getValue(Produto::class.java)
                    produto?.let {
                        listaProdutos.add(it)
                    }
                }

                Log.d("ProdutoDAO", "Lista de Produtos:")
                for (produto in listaProdutos) {
                    Log.d("ProdutoDAO", "ID: ${produto.id}, Descrição: ${produto.descricao}, Valor: ${produto.valor}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ProdutoDAO", "Erro ao ler produtos do Firebase", error.toException())
            }
        })
    }

    fun obterListaProdutos(): MutableLiveData<List<Produto>> {
        val listaProdutosLiveData = MutableLiveData<List<Produto>>()

        referencia.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listaProdutos = mutableListOf<Produto>()

                for (produtoSnapshot in snapshot.children) {
                    val produto = produtoSnapshot.getValue(Produto::class.java)
                    produto?.let {
                        listaProdutos.add(it)
                    }
                }

                // Atualiza o LiveData com a lista de produtos
                listaProdutosLiveData.value = listaProdutos
            }

            override fun onCancelled(error: DatabaseError) {
                // Se ocorrer um erro, você pode lidar com isso aqui
            }
        })

        return listaProdutosLiveData
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