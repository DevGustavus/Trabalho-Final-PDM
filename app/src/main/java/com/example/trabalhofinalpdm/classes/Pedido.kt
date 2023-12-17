package com.example.trabalhofinalpdm.classes

data class Pedido(
    var id: String? = null,
    var cliente: String? = null,
    var data: String,
    var itensPedido: Map<Int, Map<String, Any>> = emptyMap()
)