package c0d3.vitreen.app.models

data class Location(
    val name: String = "",
    var zipCode: Long? = null,
): Entity()
