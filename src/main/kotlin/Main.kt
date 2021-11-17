package un4.collections

data class Tienda(val nombre: String, val clientes: List<Clientes>) {

    fun obtenerConjuntoDeClientes(): Set<Clientes> = clientes.toSet() //Preguntar a edu sobre output

    fun obtenerCiudadesDeClientes(): Set<Ciudad> {
        val ciudades = mutableSetOf<Ciudad>()
        clientes.forEach { ciudades.add(it.ciudad) }
        return ciudades.toSet()
    }

    fun obtenerClientesPorCiudad(ciudad:Ciudad): List<Clientes>{}

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

