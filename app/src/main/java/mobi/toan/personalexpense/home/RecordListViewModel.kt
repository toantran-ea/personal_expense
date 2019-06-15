package mobi.toan.personalexpense.home

import androidx.lifecycle.ViewModel
import io.reactivex.Single
import mobi.toan.personalexpense.persistent.Record
import mobi.toan.personalexpense.persistent.RecordDao

class RecordListViewModel(private val dataSource: RecordDao) : ViewModel() {
    fun recordList(): Single<List<Record>> {
        return dataSource.getAllRecords()
    }

    fun daysSum(): Single<Pair<Double, Double>> {
        return Single.just(Pair(100000.0, 200000.0))
    }
}
