package mobi.toan.personalexpense.features.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import mobi.toan.personalexpense.R
import mobi.toan.personalexpense.ViewModelFactory
import mobi.toan.personalexpense.di.DaggerApplicationComponent
import mobi.toan.personalexpense.features.addrecord.AddRecordActivity
import mobi.toan.personalexpense.utils.NumberUtils
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: RecordListViewModel

    private val compositeDisposable = CompositeDisposable()

    private lateinit var viewAdapter: RecordAdapter

    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerApplicationComponent.create().inject(this)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewModel = viewModelFactory.create(RecordListViewModel::class.java)
        initViews()
    }

    @SuppressLint("CheckResult")
    private fun initViews() {
        fab.setOnClickListener {
            addNewRecord()
        }

        viewAdapter = RecordAdapter()
        viewManager = LinearLayoutManager(this)
        record_list.run {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        val dividerItemDecoration = DividerItemDecoration(
            this,
            DividerItemDecoration.VERTICAL
        )
        record_list.addItemDecoration(dividerItemDecoration)

        viewAdapter.onItemDeleted()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    confirmDelete(it)
                },
                { t -> Log.e(TAG, t?.message) })
            .also { compositeDisposable.add(it) }

        viewAdapter.onItemEdited()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.e("SHIT", "aaaa $it")
                    editRecord(it)
                },
                { t -> Log.e(TAG, t?.message) })
            .also { compositeDisposable.add(it) }
    }

    private fun editRecord(id: String) {
        startActivity(AddRecordActivity.getIntent(this@MainActivity, id))
    }

    private fun confirmDelete(id: String) {
        MaterialDialog(this).show {
            title(R.string.are_you_sure)
            message(R.string.delete_msg)
            positiveButton(R.string.ok) {
                deleteNote(id)
            }
            negativeButton(R.string.cancel)
        }
    }

    private fun deleteNote(id: String) {
        Observable.fromCallable {
            viewModel.deleteNoteById(id)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Toast.makeText(this, "Deleted record", Toast.LENGTH_SHORT).show()
                    refresh()
                },
                { t -> Log.e(TAG, t?.message) })
            .also { compositeDisposable.add(it) }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun refresh() {
        viewModel
            .recordList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ items -> viewAdapter.updateRecords(items) },
                { t: Throwable? -> Log.e(TAG, t?.message) })
            .also { compositeDisposable.add(it) }
        viewModel.daysSum()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                today_cost_text.text = NumberUtils.displayFormatedNumber(it.first)
                yesterday_cost_text.text = NumberUtils.displayFormatedNumber(it.second)
            },
                { t: Throwable? -> Log.e(TAG, t?.message) })
            .also { compositeDisposable.add(it) }
    }

    private fun addNewRecord() {
        startActivity(AddRecordActivity.getIntent(this, null))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
