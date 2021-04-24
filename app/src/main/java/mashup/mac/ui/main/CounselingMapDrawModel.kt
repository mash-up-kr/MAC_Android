package mashup.mac.ui.main

data class CounselingMapDrawModel(
    val id: Int = 1,
    val r: Int = 100,
    val degree: Int = 10,
    val select: Boolean = false,
    val category: String = "연애",
    val distanceKilometer: Int = 5
)