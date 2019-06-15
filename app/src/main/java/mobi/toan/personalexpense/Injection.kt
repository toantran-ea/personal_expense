package mobi.toan.personalexpense

import android.content.Context
import mobi.toan.personalexpense.persistent.AppDatabase
import mobi.toan.personalexpense.persistent.RecordDao

object Injection {

    fun provideRecordDataSource(context: Context): RecordDao {
        val database = AppDatabase.getInstance(context)
        return database.recordDao()
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val dataSource = provideRecordDataSource(context)
        return ViewModelFactory(dataSource)
    }
}
