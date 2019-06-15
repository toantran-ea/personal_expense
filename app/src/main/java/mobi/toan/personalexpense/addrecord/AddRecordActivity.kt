package mobi.toan.personalexpense.addrecord

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_record.*
import mobi.toan.personalexpense.R

class AddRecordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_record)
        initEvents()
    }

    private fun initEvents() {
        save_button.setOnClickListener {
            val cost = amount_input.text.toString().toDoubleOrNull() ?: 0.0
            val text = note_input.text.toString()
            saveRecord(cost, text)
        }
    }

    private fun saveRecord(cost: Double, text: String) {
        if (cost <= 0.0|| text.isEmpty()) {
            return Toast.makeText(this, "Invalid record found!", Toast.LENGTH_SHORT).show()
        }

    }
}
