package mobi.toan.personalexpense.features.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_record.view.*
import mobi.toan.personalexpense.R
import mobi.toan.personalexpense.datastorage.models.RecordModel
import mobi.toan.personalexpense.utils.DateTimeUtils.displayBeautyDate
import mobi.toan.personalexpense.utils.NumberUtils

class RecordAdapter(private val records: MutableList<RecordModel> = ArrayList()) :
    RecyclerView.Adapter<RecordAdapter.MyViewHolder>() {

    private val publishSubjectForDelete: PublishSubject<String> = PublishSubject.create()

    private val publishSubjectForEdit: PublishSubject<String> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val recordView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_record, parent, false) as View
        return MyViewHolder(recordView)
    }

    fun onItemDeleted(): Observable<String> {
        return publishSubjectForDelete.hide()
    }


    fun onItemEdited(): Observable<String> {
        return publishSubjectForEdit.hide()
    }

    fun updateRecords(updated: List<RecordModel>) {
        records.clear()
        records.addAll(updated)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return records.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val record = records[position]
        holder.amountText.text = NumberUtils.displayFormatedNumber(record.amount)
        holder.noteText.text = record.note
        holder.dateText.text = displayBeautyDate(holder.itemView.context, record.date.time)
        holder.deleteNote.setOnClickListener {
            publishSubjectForDelete.onNext(record.id)
        }

        holder.itemView.setOnClickListener {
            publishSubjectForEdit.onNext(record.id)
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteText: TextView = itemView.note
        val amountText: TextView = itemView.amount
        val dateText: TextView = itemView.date
        val deleteNote: ImageView = itemView.delete_note
    }
}
