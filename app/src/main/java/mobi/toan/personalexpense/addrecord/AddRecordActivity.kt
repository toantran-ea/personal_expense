package mobi.toan.personalexpense.addrecord

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

class AddRecordActivity : AppCompatActivity() {
    private lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: RecordViewModel

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_record)
        viewModelFactory = Injection.provideViewModelFactory(this)
        viewModel = viewModelFactory.create(RecordViewModel::class.java)
        initViews()
    }

    private fun initViews() {
        save_button.setOnClickListener {
            val cost = amount_input.text.toString().toDoubleOrNull() ?: 0.0
            val text = note_input.text.toString().trim()
            saveRecord(cost, text)
        }
        amount_input.addTextChangedListener(object : VoidTextWatcher() {
            override fun afterTextChanged(p0: Editable?) {
                preview_amount.text = NumberUtils.displayFormatedNumber((p0?.toString() ?: "0").toDouble())
            }
        })
    }

    private fun saveRecord(cost: Double, text: String) {
        if (cost <= 0.0 || text.isEmpty()) {
            return Toast.makeText(this, "Invalid record found!", Toast.LENGTH_SHORT).show()
        }
        viewModel.saveRecord(Record(note = text, amount = cost))
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
}
