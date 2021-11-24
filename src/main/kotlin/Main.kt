package un4.collections

data class Tienda(val nombre: String, val clientes: List<Clientes>) {

    fun obtenerConjuntoDeClientes(): Set<Clientes> = clientes.toSet()

    fun obtenerCiudadesDeClientes(): Set<Ciudad> = clientes.map { it.ciudad }.toSet()

    fun obtenerClientesPor(ciudad: Ciudad): List<Clientes> = clientes.filter { it.ciudad == ciudad }

    fun checkTodosClientesSonDe(ciudad: Ciudad): Boolean = clientes.all { it.ciudad == ciudad }

    fun hayClientesDe(ciudad: Ciudad): Boolean = clientes.any { it.ciudad == ciudad }

    fun cuentaClientesDe(ciudad: Ciudad): Int = clientes.count { it.ciudad == ciudad }

    fun encuentraClienteDe(ciudad: Ciudad): Clientes? = clientes.find { it.ciudad == ciudad }

    fun obtenerClientesOrdenadosPorPedidos(): List<Clientes> = clientes.sortedByDescending { it.pedidos.size }

    fun obtenerClientesConPedidosSinEntregar(): Set<Clientes> =
        clientes.partition { cliente -> cliente.pedidos.count { it.estaEntregado } < cliente.pedidos.count { !it.estaEntregado } }.second.toSet()

    fun obtenerProductosPedidos(): Set<Producto> = clientes.flatMap { it.pedidos }.flatMap { it.productos }.toSet()

    fun obtenerProductosPedidosPorTodos(): Set<Producto> =
        clientes.fold(obtenerProductosPedidos()) { acc, cliente -> acc.intersect(cliente.pedidos.flatMap { it.productos }.toSet()) }

    fun obtenerNumeroVecesProductoPedido(producto: Producto): Int = obtenerProductosPedidos().count { it == producto }

    fun agrupaClientesPorCiudad(): Map<Ciudad, List<Clientes>> = clientes.groupBy { it.ciudad }

    fun mapeaNombreACliente(): Map<String, Clientes> = clientes.associateBy { it.nombre }

    fun mapeaClienteACiudad(): Map<Clientes, Ciudad> = clientes.associateWith { it.ciudad }

    fun mapeaNombreClienteACiudad(): Map<String, Ciudad> = clientes.associate { Pair(it.nombre, it.ciudad) }

    fun obtenerClienteConMaxPedidos(): Clientes? = clientes.maxByOrNull { it.pedidos.size }

}

data class Clientes(val nombre: String, val ciudad: Ciudad, val pedidos: List<Pedido>) {
    override fun toString() = "$nombre from ${ciudad.nombre}"

    fun obtenerProductosPedidos(): List<Producto> = pedidos.flatMap { it.productos }

    fun encuentraProductoMasCaro(): Producto? =
        pedidos.filter { it.estaEntregado }.flatMap { it.productos }.maxByOrNull { it.precio }

    fun obtenerProductoMasCaroPedido(): Producto? = pedidos.flatMap { it.productos }.maxByOrNull { it.precio }

    fun dineroGastado(): Double = pedidos.flatMap { it.productos }.sumOf { it.precio }

}

data class Pedido(val productos: List<Producto>, val estaEntregado: Boolean)

data class Producto(val nombre: String, val precio: Double) {
    override fun toString() = "'$nombre' for $precio"
}

data class Ciudad(val nombre: String) {
    override fun toString() = nombre
}

