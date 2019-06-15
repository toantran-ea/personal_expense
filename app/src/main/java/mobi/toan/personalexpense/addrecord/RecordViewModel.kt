package mobi.toan.personalexpense.addrecord

import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Single
import mobi.toan.personalexpense.persistent.Record
import mobi.toan.personalexpense.persistent.RecordDao

class RecordViewModel(private val dataSource: RecordDao) : ViewModel() {
    fun recordById(id: String): Single<Record> {
        return dataSource.getRecordById(id)
    }

    fun saveRecord(record: Record) : Completable {
        return dataSource.insertRecord(record)
    }
}
