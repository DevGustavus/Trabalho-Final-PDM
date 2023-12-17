package com.example.trabalhofinalpdm.classes

data class Pedido(
    var id: String? = null,
    var cliente: String? = null,
    var data: String,
    var produtos: ArrayList<Produto>,
    var quantidade: ArrayList<Int>
)