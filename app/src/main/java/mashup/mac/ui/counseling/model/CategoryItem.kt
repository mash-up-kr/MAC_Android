package mashup.mac.ui.counseling.model

import mashup.mac.model.Category

data class CategoryItem(
    val category: Category,
    val isCheck: Boolean = false
)