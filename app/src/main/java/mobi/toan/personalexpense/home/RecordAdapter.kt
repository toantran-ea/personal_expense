package mobi.toan.personalexpense.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_record.view.*
import mobi.toan.personalexpense.R
import mobi.toan.personalexpense.persistent.Record
import mobi.toan.personalexpense.utils.DateTimeUtils.displayBeautyDate

class RecordAdapter(private val records: MutableList<Record> = ArrayList()) :
    RecyclerView.Adapter<RecordAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val recordView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_record, parent, false) as View
        return MyViewHolder(recordView)
    }

    fun updateRecords(updated: List<Record>) {
        records.clear()
        records.addAll(updated)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return records.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val record = records[position]
        holder.amountText.text = record.amount.toString()
        holder.noteText.text = record.note
        holder.dateText.text = displayBeautyDate(holder.itemView.context, record.date.time)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteText: TextView = itemView.note
        val amountText: TextView = itemView.amount
        val dateText: TextView = itemView.date
    }
}
