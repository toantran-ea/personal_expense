package mobi.toan.personalexpense.datastorage.models

import mobi.toan.personalexpense.datastorage.database.entity.Record
import java.util.*

data class RecordModel(
    val id: String = "",
    val note: String,
    val amount: Double,
    val created: Date = Calendar.getInstance().time,
    val updated: Date = Calendar.getInstance().time,
    val date: Date = Calendar.getInstance().time,
    val currency: String = "VND"
)

fun Record.toRecordModel(): RecordModel {
    return RecordModel(id, note, amount, created, updated, date, currency)
}

fun RecordModel.toRecord(): Record {
    return Record(
        note = note,
        amount = amount,
        created = created,
        updated = updated,
        date = date,
        currency = currency
    )
}
