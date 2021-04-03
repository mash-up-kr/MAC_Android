package mashup.mac.model

data class AnimalBadgeItem(
    val category: Category,
    val badgeCount: Int = 0,
    val isCheck: Boolean = false
)