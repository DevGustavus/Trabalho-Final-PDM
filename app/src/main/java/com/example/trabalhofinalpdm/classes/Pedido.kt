package com.example.trabalhofinalpdm.classes

data class Pedido(
    var id: Int? = null,
    var cliente: Int? = null,
    var data: String,
    var itensPedido: Map<Int, Map<String, Any>> = emptyMap()
)