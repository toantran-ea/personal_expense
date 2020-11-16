package mobi.toan.personalexpense.features.addrecord

import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Single
import mobi.toan.personalexpense.datastorage.models.RecordModel
import mobi.toan.personalexpense.datastorage.repos.RecordRepo
import javax.inject.Inject

class RecordViewModel @Inject constructor(private val dataSource: RecordRepo) : ViewModel() {
    fun recordById(id: String): Single<RecordModel> {
        return dataSource.getRecordById(id)
    }

    fun saveRecord(record: RecordModel): Completable {
        return dataSource.insertRecord(record)
    }

    fun updateRecord(record: RecordModel): Completable {
        return dataSource.insertRecord(record)
    }
}
