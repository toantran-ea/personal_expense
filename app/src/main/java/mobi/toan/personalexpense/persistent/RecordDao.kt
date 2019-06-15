package mobi.toan.personalexpense.persistent

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*

@Dao
interface RecordDao {
    @Query("SELECT * FROM records ORDER BY updated DESC")
    fun getAllRecords(): Single<List<Record>>

    @Query("SELECT * FROM records WHERE recordid = :id")
    fun getRecordById(id: String): Single<Record>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(user: Record): Completable

    @Query("DELETE FROM records")
    fun deteleAllRecords():  Completable

    @Query("DELETE FROM records where recordid = :id")
    fun deleteByIdSync(id: String)

    @Query("SELECT amount from records WHERE date BETWEEN :start AND :end")
    fun sumPeriod(start: Date, end: Date): Single<List<Double>>
}
