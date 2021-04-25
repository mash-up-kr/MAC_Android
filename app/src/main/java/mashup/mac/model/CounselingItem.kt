package mashup.mac.model

data class CounselingItem(
    val id: Int,
    val category: Category,
    val title: String = "",
    val content: String = "",
    val date: String = "2020.02.20 오전 08:00",
    val commentCount: Int = 0
)