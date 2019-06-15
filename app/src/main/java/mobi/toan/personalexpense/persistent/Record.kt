package mobi.toan.personalexpense.persistent

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "records")
data class Record(
    @PrimaryKey
    @ColumnInfo(name = "recordid")
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "note")
    val note: String,

    @ColumnInfo(name = "amount")
    val amount: Double
)
