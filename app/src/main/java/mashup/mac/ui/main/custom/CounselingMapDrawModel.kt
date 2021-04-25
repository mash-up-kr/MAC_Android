package mashup.mac.ui.main.custom

data class CounselingMapDrawModel(
    val id: Int = 1,
    var x: Int = 100,
    var y: Int = 100,
    val r: Int = 100,
    val degree: Int = 10,
    var select: Boolean = true,
    val category: String = "연애",
    val distance: Int = 5
)