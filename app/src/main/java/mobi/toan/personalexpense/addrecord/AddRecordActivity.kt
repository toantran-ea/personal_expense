package mobi.toan.personalexpense.addrecord

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add_record.*
import mobi.toan.personalexpense.Injection
import mobi.toan.personalexpense.R
import mobi.toan.personalexpense.ViewModelFactory
import mobi.toan.personalexpense.persistent.Record
import mobi.toan.personalexpense.utils.NumberUtils
import mobi.toan.personalexpense.utils.VoidTextWatcher
import java.util.*

class AddRecordActivity : AppCompatActivity() {
    private lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: RecordViewModel

    private val compositeDisposable = CompositeDisposable()

    private var selectedDate: Date = Calendar.getInstance().time

    private var editedRecordId: String? = null

    private var editedRecord: Record? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_record)
        viewModelFactory = Injection.provideViewModelFactory(this)
        viewModel = viewModelFactory.create(RecordViewModel::class.java)
        if (intent.hasExtra(RECORD_ID)) {
            editedRecordId = intent.getStringExtra(RECORD_ID)
        }
        initViews()
    }

    private fun initViews() {
        save_button.setOnClickListener {
            val cost = this@AddRecordActivity.amount_input.text.toString().toDoubleOrNull() ?: 0.0
            val text = this@AddRecordActivity.note_input.text.toString().trim()
            saveRecord(cost, text)
        }

        amount_input.addTextChangedListener(object : VoidTextWatcher() {
            override fun afterTextChanged(p0: Editable?) {
                preview_amount.text = try {
                    NumberUtils.displayFormatedNumber((p0?.toString() ?: "0").toDouble())
                } catch (ex: Exception) {
                    "0"
                }
            }
        })

        yesterday_button.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, -1)
            selectedDate = calendar.time
            save_button.callOnClick()
        }

        choose_date_button.setOnClickListener {
            MaterialDialog(this).show {
                title(R.string.pick_date)
                datePicker { _, date ->
                    selectedDate = date.time
                    this@AddRecordActivity.save_button.callOnClick()
                }
            }
        }

        editedRecordId?.let {
            viewModel.recordById(it)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ record ->
                    amount_input.setText(record.amount.toString())
                    preview_amount.text = NumberUtils.displayFormatedNumber(record.amount)
                    note_input.setText(record.note)
                    editedRecord = record
                    selectedDate = record.date
                }, { t ->
                    Log.e(TAG, t?.message)
                })
        }
    }

    private fun saveRecord(cost: Double, text: String) {
        if (cost <= 0.0 || text.isEmpty()) {
            return Toast.makeText(this, "Invalid record found!", Toast.LENGTH_SHORT).show()
        }
        val record = if (editedRecord != null) {
            val nonNull = editedRecord!!
            Record(
                nonNull.id, text, cost,
                nonNull.created,
                Calendar.getInstance().time,
                selectedDate,
                nonNull.currency
            )
        } else {
            Record(note = text, amount = cost, date = selectedDate)
        }
        viewModel.saveRecord(record)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Toast.makeText(this, getString(R.string.save_ok), Toast.LENGTH_SHORT).show()
                finish()
            }, {
                Log.e("TAG", "error $it")
            })
            .also { compositeDisposable.add(it) }
    }

    override fun onStop() {
        compositeDisposable.dispose()
        super.onStop()
    }

    companion object {
        private const val RECORD_ID = "record-id"
        private const val TAG = "AddRecordScreen"

        fun getIntent(from: Context, id: String?): Intent {
            return if (id != null) {
                Intent(from, AddRecordActivity::class.java)
                    .apply {
                        putExtra(RECORD_ID, id)
                    }
            } else {
                Intent(from, AddRecordActivity::class.java)
            }
        }
    }
}
