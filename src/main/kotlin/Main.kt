package un4.collections

data class Tienda(val nombre: String, val clientes: List<Clientes>) {

    fun obtenerConjuntoDeClientes(): Set<Clientes> = clientes.toSet()

    fun obtenerCiudadesDeClientes(): Set<Ciudad> {
        val ciudades = mutableSetOf<Ciudad>()
        clientes.forEach { ciudades.add(it.ciudad) }
        return ciudades.toSet()
    }

    fun obtenerClientesPorCiudad(ciudad: Ciudad): List<Clientes> = clientes.filter { it.ciudad == ciudad }

    fun checkTodosClientesSonDe(ciudad: Ciudad): Boolean = clientes.all { it.ciudad == ciudad }

    fun hayClientesDe(ciudad: Ciudad): Boolean = clientes.any { it.ciudad == ciudad }

    fun cuentaClientesDe(ciudad: Ciudad): Int = clientes.count { it.ciudad == ciudad }

    fun encuentraClienteDe(ciudad: Ciudad): Clientes? = clientes.find { it.ciudad == ciudad }

    fun obtenerClientesOrdenadosPorPedidos(): List<Clientes> = clientes.sortedBy { it.pedidos.size }

    fun obtenerClientesConPedidosSinEntregar(): Set<Clientes> =
        clientes.filter { it -> it.pedidos.any { !it.estaEntregado } }.toSet()

    fun obtenerProductosPedidosPorCliente(cliente: Clientes): List<Producto> {
        val listaProductos = mutableListOf<Producto>()
        cliente.pedidos.forEach { it -> it.productos.forEach { listaProductos.add(it) } }
        return listaProductos.toList()
    }

    fun obtenerProductosPedidos(): Set<Producto> {
        val setProducto = mutableSetOf<Producto>()
        clientes.forEach { it -> obtenerProductosPedidosPorCliente(it).forEach{setProducto.add(it)} }
        return setProducto.toSet()
    }
}

data class Clientes(val nombre: String, val ciudad: Ciudad, val pedidos: List<Pedido>) {
    override fun toString() = "$nombre from ${ciudad.nombre}"
}

data class Pedido(val productos: List<Producto>, val estaEntregado: Boolean)

data class Producto(val nombre: String, val precio: Double) {
    override fun toString() = "'$nombre' for $precio"
}

data class Ciudad(val nombre: String) {
    override fun toString() = nombre
}

