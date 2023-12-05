package com.example.trabalhofinalpdm.classes

class Cliente (cpf: String, nome: String, telefone: String, endereco: String) {
    var cpf: String
    var nome: String
    var telefone: String
    var endereco: String

    init {
        this.cpf = cpf
        this.nome = nome
        this.telefone = telefone
        this.endereco = endereco
    }

}