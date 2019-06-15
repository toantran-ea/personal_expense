package mobi.toan.personalexpense.home

import androidx.lifecycle.ViewModel
import io.reactivex.Single
import mobi.toan.personalexpense.persistent.Record
import mobi.toan.personalexpense.persistent.RecordDao

class RecordListViewModel(private val dataSource: RecordDao) : ViewModel() {
    fun recordList(): Single<List<Record>> {
        return dataSource.getAllRecords()
    }
}
