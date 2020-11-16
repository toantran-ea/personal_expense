package mobi.toan.personalexpense.datastorage.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import mobi.toan.personalexpense.datastorage.database.CurrencyType
import java.util.*


@Entity(tableName = "records")
data class Record(
    @PrimaryKey
    @ColumnInfo(name = "recordid")
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "note")
    val note: String,

    @ColumnInfo(name = "amount")
    val amount: Double,

    @ColumnInfo(name = "created")
    val created: Date = Calendar.getInstance().time,

    @ColumnInfo(name = "updated")
    val updated: Date = Calendar.getInstance().time,

    @ColumnInfo(name = "date")
    val date: Date = Calendar.getInstance().time,

    @ColumnInfo(name = "currency")
    val currency: String = CurrencyType.VND.text
)
