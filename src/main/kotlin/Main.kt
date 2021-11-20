package un4.collections

data class Tienda(val nombre: String, val clientes: List<Clientes>) {

    fun obtenerConjuntoDeClientes(): Set<Clientes> = clientes.toSet()

    fun obtenerCiudadesDeClientes(): Set<Ciudad> {
        val ciudades = mutableSetOf<Ciudad>()
        clientes.forEach { ciudades.add(it.ciudad) }
        return ciudades.toSet()
    }

    fun obtenerClientesPor(ciudad: Ciudad): List<Clientes> = clientes.filter { it.ciudad == ciudad }

    fun checkTodosClientesSonDe(ciudad: Ciudad): Boolean = clientes.all { it.ciudad == ciudad }

    fun hayClientesDe(ciudad: Ciudad): Boolean = clientes.any { it.ciudad == ciudad }

    fun cuentaClientesDe(ciudad: Ciudad): Int = clientes.count { it.ciudad == ciudad }

    fun encuentraClienteDe(ciudad: Ciudad): Clientes? = clientes.find { it.ciudad == ciudad }

    fun obtenerClientesOrdenadosPorPedidos(): List<Clientes> = clientes.sortedBy { it.pedidos.size }

    fun obtenerClientesConPedidosSinEntregar(): Set<Clientes> =
        clientes.filter { it -> it.pedidos.any { !it.estaEntregado } }.toSet()

    fun obtenerProductosPedidos(): Set<Producto> = clientes.flatMap { it.pedidos }.flatMap { it.productos }.toSet()

    fun obtenerProductosPedidosPorTodos(): Set<Producto> {
        val setProducto = mutableSetOf<Producto>()
        clientes.forEach { it.pedidos.forEach { it.productos.all } }
    }//todo

    fun obtenerNumeroVecesProductoPedido(producto: Producto): Int {
        var counter = 0
        clientes.forEach { cliente -> cliente.pedidos.forEach { pedido -> counter += pedido.productos.count { producto1 -> producto1 == producto } } }
        return counter
    } //TODO

    fun agrupaClientesPorCiudad(): Map<Ciudad, List<Clientes>> {
        val mapClientesCiudad = mutableMapOf<Ciudad, List<Clientes>>()
        val setCiudades = mutableSetOf<Ciudad>()
        clientes.forEach { setCiudades.add(it.ciudad) }
        setCiudades.forEach { mapClientesCiudad[it] = obtenerClientesPor(it) }
        return mapClientesCiudad.toMap()
    }

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

