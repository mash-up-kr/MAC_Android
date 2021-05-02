package mashup.mac.model

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

data class CounselingItem(
    val id: Int,
    val category: Category,
    val title: String = "",
    val content: String = "",
    val date: String = "2020.02.20 오전 08:00",
    val commentCount: Int = 0
) {
    val strDate: String
        get() {
            val oldFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            oldFormat.timeZone = TimeZone.getTimeZone("KST")
            val newFormat = SimpleDateFormat("yyyy-MM-dd a hh:mm")

            try {
                val oldDate = oldFormat.parse(date) ?: return ""
                return newFormat.format(oldDate)
            } catch (e: ParseException) {
                return ""
            }

        }
}