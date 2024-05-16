package inn.vivvvek.blogs.utils

import java.text.SimpleDateFormat
import java.util.Date

object DateUtils {

    fun parseDate(dateString: String): Date? {
        var date: Date?
        try {
            val dateFormat = SimpleDateFormat.getDateInstance()
            date = dateFormat.parse(dateString)
        } catch (e: Exception) {
            date = null
        }
        return date
    }
}