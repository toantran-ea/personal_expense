package mobi.toan.personalexpense.home

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import mobi.toan.personalexpense.persistent.Record
import mobi.toan.personalexpense.persistent.RecordDao
import mobi.toan.personalexpense.utils.DateTimeUtils.todayBounds
import mobi.toan.personalexpense.utils.DateTimeUtils.yesterdayBounds
import java.util.*

class RecordListViewModel(private val dataSource: RecordDao) : ViewModel() {
    fun recordList(): Single<List<Record>> {
        return dataSource.getAllRecords()
    }

    /**
     * Return today and yesterday sums in order
     */
    fun daysSum(): Single<Pair<Double, Double>> {
        val todayBounds = todayBounds()
        val yesterdayBounds = yesterdayBounds()
        Log.e("TAG", todayBounds.toString())
        Log.e("TAG", yesterdayBounds.toString())
        return dataSource.sumPeriod(
            Date(todayBounds.first), Date(todayBounds.second))
            .zipWith(
                dataSource.sumPeriod(Date(yesterdayBounds.first), Date(yesterdayBounds.second)),
                BiFunction { today, yesterday ->
                    Pair(today.sum(), yesterday.sum())
                }
            )
    }

    fun deleteNoteById(id: String) {
        return dataSource.deleteByIdSync(id)
    }
}
