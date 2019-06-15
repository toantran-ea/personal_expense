package mobi.toan.personalexpense.utils

import android.content.Context
import android.text.format.DateUtils
import mobi.toan.personalexpense.R

object DateTimeUtils {
    fun displayBeautyDate(context: Context, date: Long): String {
        if (DateUtils.isToday(date)) {
            return context.getString(R.string.today)
        }
        return DateUtils.getRelativeTimeSpanString(date).toString()
    }
}
