package un4.collections

data class Tienda(val nombre: String, val clientes: List<Clientes>) {

    //4.2.1
    fun obtenerConjuntoDeClientes(): Set<Clientes> = clientes.toSet()

    //4.2.2
    fun obtenerCiudadesDeClientes(): Set<Ciudad> = clientes.map { it.ciudad }.toSet()

    fun obtenerClientesPor(ciudad: Ciudad): List<Clientes> = clientes.filter { it.ciudad == ciudad }

    //4.2.3
    fun checkTodosClientesSonDe(ciudad: Ciudad): Boolean = clientes.all { it.ciudad == ciudad }

    fun hayClientesDe(ciudad: Ciudad): Boolean = clientes.any { it.ciudad == ciudad }

    fun cuentaClientesDe(ciudad: Ciudad): Int = clientes.count { it.ciudad == ciudad }

    fun encuentraClienteDe(ciudad: Ciudad): Clientes? = clientes.find { it.ciudad == ciudad }

    //4.2.4
    fun obtenerClientesOrdenadosPorPedidos(): List<Clientes> = clientes.sortedByDescending { it.pedidos.size }

    //4.2.5
    fun obtenerClientesConPedidosSinEntregar(): Set<Clientes> =
        clientes.partition { cliente -> cliente.pedidos.count { it.estaEntregado } < cliente.pedidos.count { !it.estaEntregado } }.second.toSet()

    //4.2.6
    fun obtenerProductosPedidos(): Set<Producto> = clientes.flatMap { it.pedidos }.flatMap { it.productos }.toSet()

    //4.2.7
    fun obtenerProductosPedidosPorTodos(): Set<Producto> =
        clientes.fold(obtenerProductosPedidos()) { acc, cliente ->
            acc.intersect(cliente.pedidos.flatMap { it.productos }.toSet())
        }

    //4.2.8
    fun obtenerNumeroVecesProductoPedido(producto: Producto): Int = obtenerProductosPedidos().count { it == producto }

    //4.2.9
    fun agrupaClientesPorCiudad(): Map<Ciudad, List<Clientes>> = clientes.groupBy { it.ciudad }

    //4.2.10
    fun mapeaNombreACliente(): Map<String, Clientes> = clientes.associateBy { it.nombre }

    fun mapeaClienteACiudad(): Map<Clientes, Ciudad> = clientes.associateWith { it.ciudad }

    fun mapeaNombreClienteACiudad(): Map<String, Ciudad> = clientes.associate { Pair(it.nombre, it.ciudad) }

    //4.2.11
    fun obtenerClienteConMaxPedidos(): Clientes? = clientes.maxByOrNull { it.pedidos.size }

}

data class Clientes(val nombre: String, val ciudad: Ciudad, val pedidos: List<Pedido>) {
    override fun toString() = "$nombre from ${ciudad.nombre}"

    //4.2.6
    fun obtenerProductosPedidos(): List<Producto> = pedidos.flatMap { it.productos }

    //4.2.8
    fun encuentraProductoMasCaro(): Producto? =
        pedidos.filter { it.estaEntregado }.flatMap { it.productos }.maxByOrNull { it.precio }

    //4.2.11
    fun obtenerProductoMasCaroPedido(): Producto? = pedidos.flatMap { it.productos }.maxByOrNull { it.precio }

    //4.2.12
    fun dineroGastado(): Double = pedidos.flatMap { it.productos }.sumOf { it.precio }

}

data class Pedido(val productos: List<Producto>, val estaEntregado: Boolean)

data class Producto(val nombre: String, val precio: Double) {
    override fun toString() = "'$nombre' for $precio"
}

data class Ciudad(val nombre: String) {
    override fun toString() = nombre
}

fun main(){
    val cadiz = Ciudad("Cádiz")
    val jerez = Ciudad("Jerez")
    val puertoReal = Ciudad("Puerto Real")
    val chiclana = Ciudad("Chiclana")
    val algeciras = Ciudad("Algeciras")

    val silla = Producto("Silla",40.0)
    val mesa = Producto("Mesa", 80.50)
    val lampara = Producto("Lámpara", 30.25)
    val tv = Producto("Televisión", 200.99)
    val florero = Producto("Florero", 15.00)
    val teclado = Producto ("Teclado", 31.00)

    val pedido1 = Pedido(listOf(florero,florero,lampara),estaEntregado = false)
    val pedido2 = Pedido(listOf(mesa,tv,silla,silla,silla,silla), estaEntregado = false)
    val pedido3 = Pedido(listOf(teclado,tv),estaEntregado = true)
    val pedido4 = Pedido(listOf(teclado,tv,florero,silla,mesa), true)
    val pedido5
}