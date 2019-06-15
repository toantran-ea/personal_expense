package mobi.toan.personalexpense.persistent

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface RecordDao {
    @Query("SELECT * FROM records")
    fun getAllRecords(): Single<List<Record>>

    @Query("SELECT * FROM records WHERE recordid = :id")
    fun getRecordById(id: String): Single<Record>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(user: Record): Completable

    @Query("DELETE FROM records")
    fun deteleAllRecords():  Completable
}
