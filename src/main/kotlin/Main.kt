package un4.collections

data class Tienda(val nombre: String, val clientes: List<Clientes>) {

    fun obtenerConjuntoDeClientes(): Set<Clientes> = clientes.toSet()

    fun obtenerCiudadesDeClientes(): Set<Ciudad> = clientes.map{ it.ciudad}.toSet()

    fun obtenerClientesPor(ciudad: Ciudad): List<Clientes> = clientes.filter { it.ciudad == ciudad }

    fun checkTodosClientesSonDe(ciudad: Ciudad): Boolean = clientes.all { it.ciudad == ciudad }

    fun hayClientesDe(ciudad: Ciudad): Boolean = clientes.any { it.ciudad == ciudad }

    fun cuentaClientesDe(ciudad: Ciudad): Int = clientes.count { it.ciudad == ciudad }

    fun encuentraClienteDe(ciudad: Ciudad): Clientes? = clientes.find { it.ciudad == ciudad }

    fun obtenerClientesOrdenadosPorPedidos(): List<Clientes> = clientes.sortedBy { it.pedidos.size }

    fun obtenerClientesConPedidosSinEntregar(): Set<Clientes> =
        clientes.filter { it -> it.pedidos.any { !it.estaEntregado } }.toSet()

    fun obtenerProductosPedidos(): Set<Producto> = clientes.flatMap { it.pedidos }.flatMap { it.productos }.toSet()

    fun obtenerProductosPedidosPorTodos(): Set<Producto> =  //todo

    fun obtenerNumeroVecesProductoPedido(producto: Producto): Int = obtenerProductosPedidos().count { it == producto }

    fun agrupaClientesPorCiudad(): Map<Ciudad, List<Clientes>> = clientes.groupBy { it.ciudad }

    fun mapeaNombreACliente(): Map<String, Clientes> {
        val mapClientes = mutableMapOf<String, Clientes>()
        clientes.forEach { mapClientes[it.nombre] = it }
        return mapClientes.toMap()
    }

    fun mapeaClienteACiudad(): Map<Clientes, Ciudad> {
        val mapClienteCiudad = mutableMapOf<Clientes, Ciudad>()
        clientes.forEach { mapClienteCiudad[it] = it.ciudad }
        return mapClienteCiudad.toMap()
    }

    fun mapeaNombreClienteACiudad(): Map<String,Ciudad>{
        val mapNombreClientesCiudad = mutableMapOf<String,Ciudad>()
        clientes.forEach { mapNombreClientesCiudad[it.nombre] = it.ciudad }
        return mapNombreClientesCiudad
    }

    fun obtenerClienteConMaxPedidos(): Clientes?{
        val listaClientesNumeroPedidos = mutableListOf<Pair<Clientes,Int>>()
        clientes.forEach { listaClientesNumeroPedidos.add(Pair(it,it.pedidos.size)) }
    }//TODO



}

data class Clientes(val nombre: String, val ciudad: Ciudad, val pedidos: List<Pedido>) {
    override fun toString() = "$nombre from ${ciudad.nombre}"

    fun obtenerProductoMasCaroPedido():Producto?{
        val listaProductoPrecio = mutableListOf<Pair<Producto, Double>>()
        pedidos.forEach { pedido -> pedido.productos.forEach { listaProductoPrecio.add(Pair(it, it.precio)) } }
        return listaProductoPrecio.find { par -> par.second == listaProductoPrecio.maxOf { it.second } }?.first
    }

    fun dineroGastado(): Double {
        var dineroGastado = 0.0
        pedidos.forEach { it -> it.productos.forEach { dineroGastado += it.precio } }
        return dineroGastado
    }

    fun obtenerProductosPedidos(): List<Producto> = pedidos.flatMap { it.productos }

    fun encuentraProductoMasCaro(): Producto? {
        val listaProductoPrecio = mutableListOf<Pair<Producto, Double>>()
        pedidos.filter { it.estaEntregado }
            .forEach { pedido -> pedido.productos.forEach { listaProductoPrecio.add(Pair(it, it.precio)) } }
        return listaProductoPrecio.find { par -> par.second == listaProductoPrecio.maxOf { it.second } }?.first
    }
}

data class Pedido(val productos: List<Producto>, val estaEntregado: Boolean)

data class Producto(val nombre: String, val precio: Double) {
    override fun toString() = "'$nombre' for $precio"
}

data class Ciudad(val nombre: String) {
    override fun toString() = nombre
}

