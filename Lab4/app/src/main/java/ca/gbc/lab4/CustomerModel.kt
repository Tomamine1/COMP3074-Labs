package ca.gbc.lab4

data class CustomerModel(
    val id: Int = -1,
    val name: String,
    val age: Int,
    val isActive: Boolean
) {
    override fun toString(): String {
        return "$id - $name ($age)"
    }
}

