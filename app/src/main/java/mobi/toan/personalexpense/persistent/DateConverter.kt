package mobi.toan.personalexpense.persistent

import androidx.room.TypeConverter
import java.util.*


class DateConverter {
    @TypeConverter
    fun toDate(timestamp: Long): Date {
        return Date(timestamp)
    }

    @TypeConverter
    fun toTimestamp(date: Date): Long {
        return date.time
    }
}
