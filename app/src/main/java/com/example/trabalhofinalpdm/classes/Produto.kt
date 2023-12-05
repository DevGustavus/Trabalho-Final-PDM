package com.example.trabalhofinalpdm.classes

class Produto (descricao: String, valor: Float) {

    var id = 0
    var descricao: String
    var valor: Float

    init {
        this.descricao = descricao
        this.valor = valor
    }
}