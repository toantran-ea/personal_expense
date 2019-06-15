package mobi.toan.personalexpense.utils

import android.content.Context
import android.text.format.DateUtils
import mobi.toan.personalexpense.R
import java.util.*

object DateTimeUtils {
    fun displayBeautyDate(context: Context, date: Long): String {
        if (DateUtils.isToday(date)) {
            return context.getString(R.string.today)
        }
        return DateUtils.getRelativeTimeSpanString(date).toString()
    }

    fun yesterdayBounds(): Pair<Long, Long> {
        val startYesterday = Calendar.getInstance()
        startYesterday.add(Calendar.DATE, -1)
        startYesterday.set(Calendar.HOUR, 0)
        startYesterday.set(Calendar.MINUTE, 0)
        startYesterday.set(Calendar.SECOND, 0)
        startYesterday.set(Calendar.MILLISECOND, 0)
        val startMoment = startYesterday.timeInMillis
        startYesterday.add(Calendar.DATE,  1)
        val endMoment = startYesterday.timeInMillis
        return Pair(startMoment, endMoment)
    }

    fun todayBounds(): Pair<Long, Long> {
        val startToday = Calendar.getInstance()
        val endMoment = startToday.timeInMillis
        startToday.set(Calendar.HOUR, 0)
        startToday.set(Calendar.MINUTE, 0)
        startToday.set(Calendar.SECOND, 0)
        startToday.set(Calendar.MILLISECOND, 0)
        val startMoment = startToday.timeInMillis
        return Pair(startMoment, endMoment)
    }
}
