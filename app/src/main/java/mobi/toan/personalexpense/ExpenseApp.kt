package mobi.toan.personalexpense

import android.annotation.TargetApi
import android.app.Application
import android.os.Build
import java.util.*

class ExpenseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getLocale()
        } else {
            this.resources.configuration.locale
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun getLocale(): Locale {
        return this.resources.configuration.locales.get(0)
    }

    companion object {
        var locale: Locale? = null
    }
}
