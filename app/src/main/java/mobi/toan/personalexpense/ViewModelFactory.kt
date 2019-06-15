package mobi.toan.personalexpense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mobi.toan.personalexpense.addrecord.RecordViewModel
import mobi.toan.personalexpense.home.RecordListViewModel
import mobi.toan.personalexpense.persistent.RecordDao

class ViewModelFactory(private val recordDataSource: RecordDao) :  ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecordListViewModel::class.java)) {
            return RecordListViewModel(recordDataSource) as T
        }
        if (modelClass.isAssignableFrom(RecordViewModel::class.java)) {
            return RecordViewModel(recordDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
