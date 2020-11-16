package mobi.toan.personalexpense.datastorage.repos

import io.reactivex.Completable
import io.reactivex.Single
import mobi.toan.personalexpense.datastorage.database.dao.RecordDao
import mobi.toan.personalexpense.datastorage.models.RecordModel
import mobi.toan.personalexpense.datastorage.models.toRecord
import mobi.toan.personalexpense.datastorage.models.toRecordModel
import java.util.*
import javax.inject.Inject

interface RecordRepo {
    fun getAllRecords(): Single<List<RecordModel>>

    fun getRecordById(id: String): Single<RecordModel>

    fun insertRecord(record: RecordModel): Completable

    fun deleteAllRecords(): Completable

    fun deleteByIdSync(id: String)

    fun sumPeriod(start: Date, end: Date): Single<List<Double>>
}

class RecordRepoImpl @Inject constructor(
    private val recordDao: RecordDao
) : RecordRepo {
    override fun getAllRecords(): Single<List<RecordModel>> {
        return recordDao.getAllRecords().map {
            it.map { record -> record.toRecordModel() }
        }
    }

    override fun getRecordById(id: String): Single<RecordModel> {
        return recordDao.getRecordById(id).map { it.toRecordModel() }
    }

    override fun insertRecord(record: RecordModel): Completable {
        return recordDao.insertRecord(record.toRecord())
    }

    override fun deleteAllRecords(): Completable {
        return recordDao.deleteAllRecords()
    }

    override fun deleteByIdSync(id: String) {
        return recordDao.deleteByIdSync(id)
    }

    override fun sumPeriod(start: Date, end: Date): Single<List<Double>> {
        return recordDao.sumPeriod(start, end)
    }

}