package com.example.trabalhofinalpdm.classes

data class Produto(
    var id: String? = null,
    var descricao: String,
    var valor: Float
){
    override fun toString(): String {
        return "$descricao"
    }
}