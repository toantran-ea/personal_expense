package mobi.toan.personalexpense.utils

import mobi.toan.personalexpense.ExpenseApp
import java.text.NumberFormat

object NumberUtils {
    fun withSuffix(count: Double): String {
        if (count < 1000) return "" + count
        val exp = (Math.log(count) / Math.log(1000.0)).toInt()
        return String.format(
            "%.1f %c",
            count / Math.pow(1000.0, exp.toDouble()),
            "kMGTPE"[exp - 1]
        )
    }

    fun displayFormatedNumber(value: Double): String {
        val numberFormatter = NumberFormat.getNumberInstance(ExpenseApp.locale)
        return numberFormatter.format(value)
    }
}
