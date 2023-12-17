package com.example.trabalhofinalpdm.classes

data class Cliente(
    var id: Int? = null,
    var cpf: String,
    var nome: String,
    var telefone: String,
    var endereco: String
){
    override fun toString(): String {
        return "$nome"
    }
}