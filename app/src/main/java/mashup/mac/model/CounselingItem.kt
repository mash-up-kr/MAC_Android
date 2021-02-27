package mashup.mac.model

data class CounselingItem(
    val category: Category,
    val title: String = "",
    val description: String = "",
    val date: String = "2020.02.20 오전 08:00",
    val answer: Int = 0
)