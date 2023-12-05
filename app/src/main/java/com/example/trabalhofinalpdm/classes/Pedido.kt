package com.example.trabalhofinalpdm.classes

class Pedido (cliente: Cliente, data: String) {

    var id = 0
    var cliente: Cliente
    var data: String

    init {
        this.cliente = cliente
        this.data = data
    }
}