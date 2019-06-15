package mobi.toan.personalexpense.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import mobi.toan.personalexpense.Injection
import mobi.toan.personalexpense.R
import mobi.toan.personalexpense.ViewModelFactory
import mobi.toan.personalexpense.addrecord.AddRecordActivity

class MainActivity : AppCompatActivity() {
    private lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: RecordListViewModel

    private val compositeDisposable = CompositeDisposable()

    private lateinit var viewAdapter: RecordAdapter

    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewModelFactory = Injection.provideViewModelFactory(this)
        viewModel = viewModelFactory.create(RecordListViewModel::class.java)
        initViews()
    }

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
    }

    override fun onResume() {
        super.onResume()
        viewModel.recordList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ items -> viewAdapter.updateRecords(items) },
                { t: Throwable? -> Log.e(TAG, t?.message) })
            .also { compositeDisposable.add(it) }
        viewModel.daysSum()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                today_cost_text.text = it.first.toString()
                yesterday_cost_text.text = it.second.toString()
            },
                { t: Throwable? -> Log.e(TAG, t?.message) })
            .also { compositeDisposable.add(it) }
    }

    private fun addNewRecord() {
        val intent = Intent(this, AddRecordActivity::class.java)
        startActivity(intent)
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

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
